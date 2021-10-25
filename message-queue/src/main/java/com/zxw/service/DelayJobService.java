package com.zxw.service;

import com.zxw.delay.entity.DelayJob;

/**
 * @author zxw
 * @date 2021/10/25 15:39
 */
public interface DelayJobService {
    /**
     * 添加延迟队列
     *
     * @param job 任务
     */
    void put(DelayJob job);

    /**
     * 获取消息
     *
     * @param job 任务
     */
    void pop(DelayJob job);

    /**
     * 完成
     *
     * @param job 任务
     */
    void finish(DelayJob job);

    /**
     * 删除
     *
     * @param job 任务
     */
    void delete(DelayJob job);

    /**
     * 处理消息
     * @param message
     */
    void delayHandler(String message);
}
