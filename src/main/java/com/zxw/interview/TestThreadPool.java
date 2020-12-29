package com.zxw.interview;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zxw
 * @date 2020-12-27 22:02
 */
public class TestThreadPool implements Executor {
    private final AtomicInteger ctl = new AtomicInteger(0);
    private volatile int corePoolSize;
    private volatile int maxPoolSize;
    // 工作线程池
    private HashSet set = new HashSet();
    // 队列
    private BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue();
    // 拒绝策略
    private RejectedExecutionHandler rejectedExecutionHandler;

    public TestThreadPool(int corePoolSize, int maxPoolSize,BlockingQueue blockingQueue) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.blockingQueue = blockingQueue;
    }
    @Override
    public void execute(Runnable command) {
        int c = ctl.get();
        if(c < corePoolSize){

        }
    }
}
