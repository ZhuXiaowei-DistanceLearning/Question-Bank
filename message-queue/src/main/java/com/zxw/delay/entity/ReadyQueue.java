package com.zxw.delay.entity;

import com.zxw.delay.enums.DelayJobEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

@Component
@Slf4j
public class ReadyQueue {
    @Autowired
    private JobPool jobPool;

    private String NAME = "process.queue";
    private LinkedBlockingQueue<Job> queue = new LinkedBlockingQueue();

    private String getKey(String topic) {
        return NAME + topic;
    }

    /**
     * 设置任务
     *
     * @param delayJob
     */
    public void pushJob(Job delayJob) {
        log.info("执行队列添加任务:{}", delayJob);
        if (delayJob != null) {
            jobPool.setJobStatus(delayJob.getId(), DelayJobEnums.READY.getValue());
            queue.add(delayJob);
        }
    }

    /**
     * 移除并获得任务
     *
     * @param topic
     * @return
     */
    public Job getJob(String topic) {
        return queue.poll();
    }

}