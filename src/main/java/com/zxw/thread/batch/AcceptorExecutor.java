package com.zxw.thread.batch;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 具有内部线程的活动对象，该活动对象接受来自客户端的任务，并以基于拉的方式将其分配给工作人员。 工作者在可用时会明确要求一个或一批项目。 这样可以确保要处理的数据始终是最新的，并且不会进行过时的数据处理。
 * 任务识别
 * 传递给处理的每个任务都有一个对应的任务ID。 此ID用于删除重复项（将较旧的副本替换为较新的副本）。
 * 再加工
 * 如果工作人员的数据处理失败，并且故障本质上是暂时的，则工作人员会将任务放回AcceptorExecutor。 该数据将与当前工作负载合并，如果已经接收到较新的版本，则可能会将其丢弃。
 *
 * @author zxw
 * @date 2021-01-01 21:41
 */
public class AcceptorExecutor<ID, T> {
    private final int maxBufferSize;
    private final int maxBatchingSize;
    private final long maxBatchingDelay;
    private final AtomicBoolean isShutdown = new AtomicBoolean(false);

    private final BlockingQueue<TaskHolder<ID, T>> acceptorQueue = new LinkedBlockingQueue<>();
    private final BlockingDeque<TaskHolder<ID, T>> reprocessQueue = new LinkedBlockingDeque<>();
    private final Thread acceptorThread;

    private final Map<ID, TaskHolder<ID, T>> pendingTasks = new HashMap<>();
    private final Deque<ID> processingOrder = new LinkedList<>();

    private final Semaphore singleItemWorkRequests = new Semaphore(0);
    private final BlockingQueue<TaskHolder<ID, T>> singleItemWorkQueue = new LinkedBlockingQueue<>();

    private final Semaphore batchWorkRequests = new Semaphore(0);
    private final BlockingQueue<List<TaskHolder<ID, T>>> batchWorkQueue = new LinkedBlockingQueue<>();

    volatile long overriddenTasks;

    volatile long queueOverflows;

    volatile long expiredTasks;

    volatile long acceptedTasks;

    volatile long replayedTasks;


//    private final TrafficShaper trafficShaper;


    AcceptorExecutor(String id,
                     int maxBufferSize,
                     int maxBatchingSize,
                     long maxBatchingDelay,
                     long congestionRetryDelayMs,
                     long networkFailureRetryMs) {
        this.maxBufferSize = maxBufferSize;
        this.maxBatchingSize = maxBatchingSize;
        this.maxBatchingDelay = maxBatchingDelay;
//        this.trafficShaper = new TrafficShaper(congestionRetryDelayMs, networkFailureRetryMs);

        ThreadGroup threadGroup = new ThreadGroup("eurekaTaskExecutors");
        this.acceptorThread = new Thread(threadGroup, new AcceptorRunner(), "TaskAcceptor-" + id);
        this.acceptorThread.setDaemon(true);
        this.acceptorThread.start();
//        final double[] percentiles = {50.0, 95.0, 99.0, 99.5};
//        final StatsConfig statsConfig = new StatsConfig.Builder()
//                .withSampleSize(1000)
//                .withPercentiles(percentiles)
//                .withPublishStdDev(true)
//                .build();
//        final MonitorConfig config = MonitorConfig.builder(METRIC_REPLICATION_PREFIX + "batchSize").build();
//        this.batchSizeMetric = new StatsTimer(config, statsConfig);
//        try {
//            Monitors.registerObject(id, this);
//        } catch (Throwable e) {
//            logger.warn("Cannot register servo monitor for this object", e);
//        }
    }

    void process(ID id, T task, long expiryTime) {
        acceptorQueue.add(new TaskHolder<ID, T>(id, task, expiryTime));
        acceptedTasks++;
    }

    void reprocess(List<TaskHolder<ID, T>> holders, TaskProcessor.ProcessingResult processingResult) {
        reprocessQueue.addAll(holders);
        replayedTasks += holders.size();
//        trafficShaper.registerFailure(processingResult);
    }

    void reprocess(TaskHolder<ID, T> taskHolder, TaskProcessor.ProcessingResult processingResult) {
        reprocessQueue.add(taskHolder);
        replayedTasks++;
//        trafficShaper.registerFailure(processingResult);
    }

    void shutdown() {
        if (isShutdown.compareAndSet(false, true)) {
            acceptorThread.interrupt();
        }
    }

    BlockingQueue<List<TaskHolder<ID, T>>> requestWorkItems() {
        batchWorkRequests.release();
        return batchWorkQueue;
    }

    BlockingQueue<TaskHolder<ID, T>> requestWorkItem() {
        singleItemWorkRequests.release();
        return singleItemWorkQueue;
    }

    class AcceptorRunner implements Runnable {
        @Override
        public void run() {
            long scheduleTime = 0;
            while (!isShutdown.get()) {
                try {
                    drainInputQueues();

                    int totalItems = processingOrder.size();

                    long now = System.currentTimeMillis();
//                    if (scheduleTime < now) {
//                        scheduleTime = now + trafficShaper.transmissionDelay();
//                    }
//                    if (scheduleTime <= now) {
//                        assignBatchWork();
//                        assignSingleItemWork();
//                    }

                    // If no worker is requesting data or there is a delay injected by the traffic shaper,
                    // sleep for some time to avoid tight loop.
                    if (totalItems == processingOrder.size()) {
                        Thread.sleep(10);
                    }
                } catch (InterruptedException ex) {
                    // Ignore
                } catch (Throwable e) {
                    // Safe-guard, so we never exit this loop in an uncontrolled way.
//                    logger.warn("Discovery AcceptorThread error", e);
                }
            }
        }

        private boolean isFull() {
            return pendingTasks.size() >= maxBufferSize;
        }

        private void drainInputQueues() throws InterruptedException {
            do {
                drainReprocessQueue();
                drainAcceptorQueue();

                if (!isShutdown.get()) {
                    // If all queues are empty, block for a while on the acceptor queue
                    if (reprocessQueue.isEmpty() && acceptorQueue.isEmpty() && pendingTasks.isEmpty()) {
                        TaskHolder<ID, T> taskHolder = acceptorQueue.poll(10, TimeUnit.MILLISECONDS);
                        if (taskHolder != null) {
                            appendTaskHolder(taskHolder);
                        }
                    }
                }
            } while (!reprocessQueue.isEmpty() || !acceptorQueue.isEmpty() || pendingTasks.isEmpty());
        }

        private void drainAcceptorQueue() {
            while (!acceptorQueue.isEmpty()) {
                appendTaskHolder(acceptorQueue.poll());
            }
        }

        private void drainReprocessQueue() {
            long now = System.currentTimeMillis();
            while (!reprocessQueue.isEmpty() && !isFull()) {
                TaskHolder<ID, T> taskHolder = reprocessQueue.pollLast();
                ID id = taskHolder.getId();
                if (taskHolder.getExpiryTime() <= now) {
                    expiredTasks++;
                } else if (pendingTasks.containsKey(id)) {
                    overriddenTasks++;
                } else {
                    pendingTasks.put(id, taskHolder);
                    processingOrder.addFirst(id);
                }
            }
            if (isFull()) {
                queueOverflows += reprocessQueue.size();
                reprocessQueue.clear();
            }
        }

        private void appendTaskHolder(TaskHolder<ID, T> taskHolder) {
            if (isFull()) {
                pendingTasks.remove(processingOrder.poll());
                queueOverflows++;
            }
            TaskHolder<ID, T> previousTask = pendingTasks.put(taskHolder.getId(), taskHolder);
            if (previousTask == null) {
                processingOrder.add(taskHolder.getId());
            } else {
                overriddenTasks++;
            }
        }

        void assignSingleItemWork() {
            if (!processingOrder.isEmpty()) {
                if (singleItemWorkRequests.tryAcquire(1)) {
                    long now = System.currentTimeMillis();
                    while (!processingOrder.isEmpty()) {
                        ID id = processingOrder.poll();
                        TaskHolder<ID, T> holder = pendingTasks.remove(id);
                        if (holder.getExpiryTime() > now) {
                            singleItemWorkQueue.add(holder);
                            return;
                        }
                        expiredTasks++;
                    }
                    singleItemWorkRequests.release();
                }
            }
        }

        void assignBatchWork() {
            if (hasEnoughTasksForNextBatch()) {
                if (batchWorkRequests.tryAcquire(1)) {
                    long now = System.currentTimeMillis();
                    int len = Math.min(maxBatchingSize, processingOrder.size());
                    List<TaskHolder<ID, T>> holders = new ArrayList<>(len);
                    while (holders.size() < len && !processingOrder.isEmpty()) {
                        ID id = processingOrder.poll();
                        TaskHolder<ID, T> holder = pendingTasks.remove(id);
                        if (holder.getExpiryTime() > now) {
                            holders.add(holder);
                        } else {
                            expiredTasks++;
                        }
                    }
                    if (holders.isEmpty()) {
                        batchWorkRequests.release();
                    } else {
//                        batchSizeMetric.record(holders.size(), TimeUnit.MILLISECONDS);
                        batchWorkQueue.add(holders);
                    }
                }
            }
        }

        private boolean hasEnoughTasksForNextBatch() {
            if (processingOrder.isEmpty()) {
                return false;
            }
            if (pendingTasks.size() >= maxBufferSize) {
                return true;
            }

            TaskHolder<ID, T> nextHolder = pendingTasks.get(processingOrder.peek());
            long delay = System.currentTimeMillis() - nextHolder.getSubmitTimestamp();
            return delay >= maxBatchingDelay;
        }
    }
}
