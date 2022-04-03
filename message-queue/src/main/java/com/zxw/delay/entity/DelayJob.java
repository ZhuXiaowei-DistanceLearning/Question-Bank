package com.zxw.delay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DelayJob implements Serializable {
    /**
     * 延迟任务的唯一标识
     */
    private String jodId;

    /**
     * 任务的执行时间
     */
    private long delayDate;

    public DelayJob(Job job) {
        this.jodId = job.getId();
        this.delayDate = System.currentTimeMillis() + job.getDelayTime();
    }
}