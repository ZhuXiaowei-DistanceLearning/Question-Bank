package com.zxw.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.omg.CORBA.TIMEOUT;
import sun.nio.ch.ThreadPool;

import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * @author zxw
 * @date 2020/8/25 22:36
 */
public class DiscoveryClient {
    private String name;
    private static final String PERFIX = "DiscoveryClient_";
    private final ScheduledExecutorService scheduler;
    private final ThreadPoolExecutor heartbeatExecutor;
    private final ThreadPoolExecutor cacheRefreshExecutor;

    public DiscoveryClient(String name) {
        this.scheduler = Executors.newScheduledThreadPool(2,
                new ThreadFactoryBuilder()
                        .setNameFormat("DiscoveryClient-%")
                        .setDaemon(true)
                        .build());
        this.heartbeatExecutor = new ThreadPoolExecutor(1, 2, 0, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactoryBuilder()
                .setNameFormat("DiscoveryClient-HeartbeatExecutor-%d")
                .setDaemon(true)
                .build());

        this.cacheRefreshExecutor = new ThreadPoolExecutor(1, 2, 0, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactoryBuilder()
                .setNameFormat("DiscoveryClient-HeartbeatExecutor-%d")
                .setDaemon(true)
                .build());

        initScheduledTasks();

    }

    public void initScheduledTasks() {
        scheduler.schedule(new TimerTask() {
            @Override
            public void run() {
                heartbeatExecutor.submit(new HeartbeatThread());
            }
        }, 30, TimeUnit.SECONDS);

        scheduler.schedule(new TimerTask() {
            @Override
            public void run() {
                Future<?> future = null;
                try {
                    cacheRefreshExecutor.submit(new CacheRefreshThread());
                } catch (Throwable e) {

                }
            }
        }, 30, TimeUnit.SECONDS);
    }

    private class HeartbeatThread implements Runnable {
        @Override
        public void run() {
            System.out.println("发送心跳");
        }
    }

    private class CacheRefreshThread implements Runnable {
        @Override
        public void run() {
            System.out.println("拉取注册表");
        }
    }
}
