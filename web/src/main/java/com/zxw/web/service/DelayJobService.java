package com.zxw.web.service;

import com.zxw.delay.entity.Job;

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
    void put(Job job);

    /**
     * 获取消息
     *
     * @param job 任务
     */
    void pop(Job job);

    /**
     * 完成
     *
     * @param job 任务
     */
    void finish(Job job);

    /**
     * 删除
     *
     * @param job 任务
     */
    void delete(Job job);

    /**
     * 处理消息
     * @param message
     */
    void delayHandler(String message);
}
