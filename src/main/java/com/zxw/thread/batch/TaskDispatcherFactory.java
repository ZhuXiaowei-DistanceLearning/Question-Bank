package com.zxw.thread.batch;

/**
 * @author zxw
 * @date 2021-01-03 21:21
 */
public class TaskDispatcherFactory {
    public static <ID, T> TaskDispatcher<ID, T> createNonBatchingTaskDispatcher(String id,
                                                                                int maxBufferSize,
                                                                                int workerCount,
                                                                                long maxBatchingDelay,
                                                                                long congestionRetryDelayMs,
                                                                                long networkFailureRetryMs,
                                                                                TaskProcessor<T> taskProcessor) {
        return new SimpleTaskDispatcher<>(id, maxBufferSize, 1, workerCount, networkFailureRetryMs, congestionRetryDelayMs, networkFailureRetryMs, taskProcessor);
    }

    public static <ID, T> TaskDispatcher<ID, T> createBatchingTaskDispatcher(String id,
                                                                             int maxBufferSize,
                                                                             int workloadSize,
                                                                             int workerCount,
                                                                             long maxBatchingDelay,
                                                                             long congestionRetryDelayMs,
                                                                             long networkFailureRetryMs,
                                                                             TaskProcessor<T> taskProcessor) {
        return new SimpleTaskDispatcher<>(id, maxBufferSize, workloadSize, workerCount, maxBatchingDelay, congestionRetryDelayMs, networkFailureRetryMs, taskProcessor);
    }
}
