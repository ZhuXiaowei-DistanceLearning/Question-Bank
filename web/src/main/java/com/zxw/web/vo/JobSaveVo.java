package com.zxw.web.vo;

import lombok.Data;

/**
 * @author zxw
 * @date 2021/10/25 15:17
 */
@Data
public class JobSaveVo {
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
}
