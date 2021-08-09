package com.zxw.thread;

import com.google.common.base.Optional;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zxw
 * @date 2021-01-01 21:43
 */
public class AsyncSequentialExecutor {
    /**
     * Index for thread naming
     */
    private static final AtomicInteger INDEX = new AtomicInteger(0);

    /**
     * Result status, if events are executed successfully in sequential manner, then return this by default
     */
    public enum ResultStatus {
        DONE
    }

    /**
     * Run a sequential events in asynchronous way. An result holder will be returned to the caller.
     * If calling is successful, then will return {@link ResultStatus#DONE}, or else exception will
     * be thrown and {@link AsyncResult} will be filled with the error.
     *
     * @param events sequential events.
     * @return result holder.
     */
    public AsyncResult<ResultStatus> run() {
        return run(new Callable<ResultStatus>() {
            @Override
            public ResultStatus call() throws Exception {
//                if (events == null || CollectionUtils.isNullOrEmpty(events.getEventList())) {
//                    throw new IllegalArgumentException("SequentialEvents does not contain any event to run");
//                }
//                for (SingleEvent singleEvent : events.getEventList()) {
//                    new Backoff(singleEvent.getIntervalTimeInMs()).backoff();
//                    singleEvent.getAction().execute();
//                }
                return ResultStatus.DONE;
            }
        });
    }

    /**
     * Run task in a thread.
     *
     * @param task task to run.
     */
    protected <T> AsyncResult<T> run(Callable<T> task) {
//        final AsyncResult<T> result = new ConcreteAsyncResult<T>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                T value = null;
                Optional<Exception> e = Optional.absent();
                try {
                    value = task.call();
//                    result.handleResult(value);
                } catch (Exception e1) {
                    e = Optional.of(e1);
//                    result.handleError(e1);
                }
            }
        }, "AsyncSequentialExecutor-" + INDEX.incrementAndGet()).start();
        return null;
    }
}
