package com.zxw.constants;

/**
 * @author zxw
 * @date 2021/10/26 10:26
 */
public class RedisKeyConsts {
    /**
     * 一组以时间为维度的有序队列，用来存放所有需要延迟的／已经被reserve的Job（这里只存放Job Id）。
     */
    public static final String DELAY_BUCKET = "delay_bucket";
    /**
     * 存放处于Ready状态的Job（这里只存放Job Id），以供消费程序消费。
     */
    public static final String READY_QUEUE = "delay_ready_queue";
    /**
     * Job Pool用来存放所有Job的元信息。
     */
    public static final String JOB_POOL = "delay_job_pool";
}
