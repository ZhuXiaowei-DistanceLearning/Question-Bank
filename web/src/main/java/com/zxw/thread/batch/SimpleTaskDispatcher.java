package com.zxw.thread.batch;

import org.apache.poi.ss.formula.functions.T;

/**
 * @author zxw
 * @date 2021-01-03 19:34
 */
public class SimpleTaskDispatcher<ID, T> implements TaskDispatcher<ID, T> {
    private static final long RETRY_SLEEP_TIME_MS = 100;

    /**
     * 服务不可用阻塞时长
     */
    private static final long SERVER_UNAVAILABLE_SLEEP_TIME_MS = 1000;

    /**
     * 延迟时间
     */
    private static final long MAX_BATCHING_DELAY_MS = 500;

    /**
     * 最大数量
     */
    private static final int BATCH_SIZE = 250;
    private final AcceptorExecutor<ID, T> acceptorExecutor;
    private final TaskExecutors<ID, T> taskExecutor;

    public SimpleTaskDispatcher(String id,
                                int maxBufferSize,
                                int workloadSize,
                                int workerCount,
                                long maxBatchingDelay,
                                long congestionRetryDelayMs,
                                long networkFailureRetryMs,
                                TaskProcessor<T> taskProcessor) {
        this.acceptorExecutor = new AcceptorExecutor<>(
                id, maxBufferSize, workloadSize, maxBatchingDelay, congestionRetryDelayMs, networkFailureRetryMs
        );
        this.taskExecutor = TaskExecutors.batchExecutors(id, workerCount, taskProcessor, acceptorExecutor);
    }

    public SimpleTaskDispatcher(String id,
                                TaskProcessor<T> taskProcessor) {
        this.acceptorExecutor = new AcceptorExecutor<>(id);
        this.taskExecutor = TaskExecutors.batchExecutors(id, 3, taskProcessor, acceptorExecutor);
    }

    @Override
    public void process(ID id, T task, long expiryTime) {
        acceptorExecutor.process(id, task, expiryTime);
    }

    @Override
    public void shutdown() {
        acceptorExecutor.shutdown();
        taskExecutor.shutdown();
    }
}
