package com.zxw.delay.entity;

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
public class DelayJob {
    /**
     * Job的唯一标识。用来检索和删除指定的Job信息。
     */
    private String id;
    /**
     * Job类型。可以理解成具体的业务名称。
     */
    private String topic;
    /**
     * Job需要延迟的时间。单位：秒。（服务端会将其转换为绝对时间）
     */
    private String delay;
    /**
     * Job的内容，供消费者做具体的业务处理，以json格式存储。
     */
    private String body;
    /**
     * 为了保证消息传输的可靠性。
     * Job执行超时时间。单位：秒。
     */
    private String ttr;
}
