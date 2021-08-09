package com.zxw.thread.batch;

import java.util.List;

/**
 * @author zxw
 * @date 2021-01-03 13:34
 */
public interface TaskProcessor<T> {
    /**
     * Success成功
     * Congestion阻塞
     * TransientError处理失败，但稍后应重试
     * PermanentError处理失败，并且不可恢复
     */
    enum ProcessingResult {
        Success, Congestion, TransientError, PermanentError
    }

    /**
     * 处理单个任务
     * @param task
     * @return
     */
    ProcessingResult process(T task);

    /**
     * 一次运行一组任务
     * @param tasks
     * @return
     */
    ProcessingResult process(List<T> tasks);
}
