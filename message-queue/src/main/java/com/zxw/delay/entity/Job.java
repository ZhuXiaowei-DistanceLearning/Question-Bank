package com.zxw.delay.entity;

import com.zxw.delay.enums.DelayJobEnums;
import lombok.Data;

/**
 * 每个Job只会处于某一个状态下：
 * ready：可执行状态，等待消费。
 * delay：不可执行状态，等待时钟周期。
 * reserved：已被消费者读取，但还未得到消费者的响应（delete、finish）。
 * deleted：已被消费完成或者已被删除。
 *
 * @author zxw
 * @date 2021/10/25 15:17
 */
@Data
public class Job {
    /**
     * Job的唯一标识。用来检索和删除指定的Job信息。
     */
    private String id;
    /**
     * Job类型。可以理解成具体的业务名称。
     */
    private String topic;
    /**
     * 任务的延迟时间
     */
    private long delayTime;
    /**
     * 任务的延迟时间
     */
    private long currentTime;
    /**
     * 任务的执行超时时间
     */
    private long ttrTime;
    /**
     * Job的内容，供消费者做具体的业务处理，以json格式存储。
     */
    private String body;
    /**
     * 重试次数
     */
    private int retryCount;
    /**
     * job状态
     */
    private String status = DelayJobEnums.DELAY.getValue();
}
