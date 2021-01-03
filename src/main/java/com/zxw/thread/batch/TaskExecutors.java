package com.zxw.thread.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TaskExecutors实例拥有许多与AcceptorExecutor合作的辅助线程。 只要有工作请求，每个工作线程Executor发送一个工作请求，并在提供任务后对其进行处理。
 * @author zxw
 * @date 2021-01-01 21:44
 */
public class TaskExecutors<ID, T> {
    private static final Logger logger = LoggerFactory.getLogger(TaskExecutors.class);
    private final AtomicBoolean isShutdown;
    private final List<Thread> workerThreads;

    TaskExecutors(WorkerRunnableFactory<ID, T> workerRunnableFactory, int workerCount, AtomicBoolean isShutdown) {
        this.isShutdown = isShutdown;
        this.workerThreads = new ArrayList<>();

        ThreadGroup threadGroup = new ThreadGroup("eurekaTaskExecutors");
        for (int i = 0; i < workerCount; i++) {
            WorkerRunnable<ID, T> runnable = workerRunnableFactory.create(i);
            Thread workerThread = new Thread(threadGroup, runnable, runnable.getWorkerName());
            workerThreads.add(workerThread);
            workerThread.setDaemon(true);
            workerThread.start();
        }
    }

    void shutdown() {
        if (isShutdown.compareAndSet(false, true)) {
            for (Thread workerThread : workerThreads) {
                workerThread.interrupt();
            }
        }
    }

    static <ID, T> TaskExecutors<ID, T> singleItemExecutors(final String name,
                                                            int workerCount,
                                                            final TaskProcessor<T> processor,
                                                            final AcceptorExecutor<ID, T> acceptorExecutor) {
        final AtomicBoolean isShutdown = new AtomicBoolean();
        final TaskExecutorMetrics metrics = new TaskExecutorMetrics(name);
        return new TaskExecutors<>(new WorkerRunnableFactory<ID, T>() {
            @Override
            public WorkerRunnable<ID, T> create(int idx) {
                return new SingleTaskWorkerRunnable<>("TaskNonBatchingWorker-" + name + '-' + idx, isShutdown, metrics, processor, acceptorExecutor);
            }
        }, workerCount, isShutdown);
    }

    static <ID, T> TaskExecutors<ID, T> batchExecutors(final String name,
                                                       int workerCount,
                                                       final TaskProcessor<T> processor,
                                                       final AcceptorExecutor<ID, T> acceptorExecutor) {
        final AtomicBoolean isShutdown = new AtomicBoolean();
        final TaskExecutorMetrics metrics = new TaskExecutorMetrics(name);
        return new TaskExecutors<>(new WorkerRunnableFactory<ID, T>() {
            @Override
            public WorkerRunnable<ID, T> create(int idx) {
                return new BatchWorkerRunnable<>("TaskBatchingWorker-" +name + '-' + idx, isShutdown, metrics, processor, acceptorExecutor);
            }
        }, workerCount, isShutdown);
    }

    static class TaskExecutorMetrics {

        volatile long numberOfSuccessfulExecutions;

        volatile long numberOfTransientError;

        volatile long numberOfPermanentError;

        volatile long numberOfCongestionIssues;

//        final StatsTimer taskWaitingTimeForProcessing;

        TaskExecutorMetrics(String id) {
//            final double[] percentiles = {50.0, 95.0, 99.0, 99.5};
//            final StatsConfig statsConfig = new StatsConfig.Builder()
//                    .withSampleSize(1000)
//                    .withPercentiles(percentiles)
//                    .withPublishStdDev(true)
//                    .build();
//            final MonitorConfig config = MonitorConfig.builder(METRIC_REPLICATION_PREFIX + "executionTime").build();
//            taskWaitingTimeForProcessing = new StatsTimer(config, statsConfig);

//            try {
//                Monitors.registerObject(id, this);
//            } catch (Throwable e) {
//                logger.warn("Cannot register servo monitor for this object", e);
//            }
        }

        void registerTaskResult(TaskProcessor.ProcessingResult result, int count) {
            switch (result) {
                case Success:
                    numberOfSuccessfulExecutions += count;
                    break;
                case TransientError:
                    numberOfTransientError += count;
                    break;
                case PermanentError:
                    numberOfPermanentError += count;
                    break;
                case Congestion:
                    numberOfCongestionIssues += count;
                    break;
            }
        }

        <ID, T> void registerExpiryTime(TaskHolder<ID, T> holder) {
//            taskWaitingTimeForProcessing.record(System.currentTimeMillis() - holder.getSubmitTimestamp(), TimeUnit.MILLISECONDS);
        }

        <ID, T> void registerExpiryTimes(List<TaskHolder<ID, T>> holders) {
            long now = System.currentTimeMillis();
            for (TaskHolder<ID, T> holder : holders) {
//                taskWaitingTimeForProcessing.record(now - holder.getSubmitTimestamp(), TimeUnit.MILLISECONDS);
            }
        }
    }

    interface WorkerRunnableFactory<ID, T> {
        WorkerRunnable<ID, T> create(int idx);
    }

    abstract static class WorkerRunnable<ID, T> implements Runnable {
        final String workerName;
        final AtomicBoolean isShutdown;
        final TaskExecutorMetrics metrics;
        final TaskProcessor<T> processor;
        final AcceptorExecutor<ID, T> taskDispatcher;

        WorkerRunnable(String workerName,
                       AtomicBoolean isShutdown,
                       TaskExecutorMetrics metrics,
                       TaskProcessor<T> processor,
                       AcceptorExecutor<ID, T> taskDispatcher) {
            this.workerName = workerName;
            this.isShutdown = isShutdown;
            this.metrics = metrics;
            this.processor = processor;
            this.taskDispatcher = taskDispatcher;
        }

        String getWorkerName() {
            return workerName;
        }
    }

    static class BatchWorkerRunnable<ID, T> extends WorkerRunnable<ID, T> {

        BatchWorkerRunnable(String workerName,
                            AtomicBoolean isShutdown,
                            TaskExecutorMetrics metrics,
                            TaskProcessor<T> processor,
                            AcceptorExecutor<ID, T> acceptorExecutor) {
            super(workerName, isShutdown, metrics, processor, acceptorExecutor);
        }

        @Override
        public void run() {
            try {
                while (!isShutdown.get()) {
                    List<TaskHolder<ID, T>> holders = getWork();
                    metrics.registerExpiryTimes(holders);

                    List<T> tasks = getTasksOf(holders);
                    TaskProcessor.ProcessingResult result = processor.process(tasks);
                    switch (result) {
                        case Success:
                            break;
                        case Congestion:
                        case TransientError:
                            taskDispatcher.reprocess(holders, result);
                            break;
                        case PermanentError:
                            logger.warn("Discarding {} tasks of {} due to permanent error", holders.size(), workerName);
                    }
                    metrics.registerTaskResult(result, tasks.size());
                }
            } catch (InterruptedException e) {
                // Ignore
            } catch (Throwable e) {
                // Safe-guard, so we never exit this loop in an uncontrolled way.
                logger.warn("Discovery WorkerThread error", e);
            }
        }

        private List<TaskHolder<ID, T>> getWork() throws InterruptedException {
            BlockingQueue<List<TaskHolder<ID, T>>> workQueue = taskDispatcher.requestWorkItems();
            List<TaskHolder<ID, T>> result;
            do {
                result = workQueue.poll(1, TimeUnit.SECONDS);
            } while (!isShutdown.get() && result == null);
            return (result == null) ? new ArrayList<>() : result;
        }


        private List<T> getTasksOf(List<TaskHolder<ID, T>> holders) {
            List<T> tasks = new ArrayList<>(holders.size());
            for (TaskHolder<ID, T> holder : holders) {
                tasks.add(holder.getTask());
            }
            return tasks;
        }
    }

    static class SingleTaskWorkerRunnable<ID, T> extends WorkerRunnable<ID, T> {

        SingleTaskWorkerRunnable(String workerName,
                                 AtomicBoolean isShutdown,
                                 TaskExecutorMetrics metrics,
                                 TaskProcessor<T> processor,
                                 AcceptorExecutor<ID, T> acceptorExecutor) {
            super(workerName, isShutdown, metrics, processor, acceptorExecutor);
        }

        @Override
        public void run() {
            try {
                while (!isShutdown.get()) {
                    BlockingQueue<TaskHolder<ID, T>> workQueue = taskDispatcher.requestWorkItem();
                    TaskHolder<ID, T> taskHolder;
                    while ((taskHolder = workQueue.poll(1, TimeUnit.SECONDS)) == null) {
                        if (isShutdown.get()) {
                            return;
                        }
                    }
                    metrics.registerExpiryTime(taskHolder);
                    if (taskHolder != null) {
                        TaskProcessor.ProcessingResult result = processor.process(taskHolder.getTask());
                        switch (result) {
                            case Success:
                                break;
                            case Congestion:
                            case TransientError:
                                taskDispatcher.reprocess(taskHolder, result);
                                break;
                            case PermanentError:
                                logger.warn("Discarding a task of {} due to permanent error", workerName);
                        }
                        metrics.registerTaskResult(result, 1);
                    }
                }
            } catch (InterruptedException e) {
                // Ignore
            } catch (Throwable e) {
                // Safe-guard, so we never exit this loop in an uncontrolled way.
                logger.warn("Discovery WorkerThread error", e);
            }
        }
    }
}
