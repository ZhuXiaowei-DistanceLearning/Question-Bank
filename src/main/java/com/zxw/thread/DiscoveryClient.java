package com.zxw.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.omg.CORBA.TIMEOUT;
import sun.nio.ch.ThreadPool;

import java.sql.Time;
import java.time.LocalDateTime;
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
    private String status = "DOWN";

    public DiscoveryClient(String name) {
        this.name = name;
        this.scheduler = Executors.newScheduledThreadPool(2,
                new ThreadFactoryBuilder()
                        .setNameFormat("DiscoveryClient-%d")
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

        scheduler.schedule(new TimerSuperisorTask(
                "heartbeat",
                scheduler,
                heartbeatExecutor,
                10,
                TimeUnit.SECONDS,
                10,
                new HeartbeatThread()
        ), 10, TimeUnit.SECONDS);

        scheduler.schedule(new TimerSuperisorTask(
                "cacheRefresh",
                scheduler,
                cacheRefreshExecutor,
                10,
                TimeUnit.SECONDS,
                10,
                new CacheRefreshThread()
        ), 10, TimeUnit.SECONDS);
    }

    private class HeartbeatThread implements Runnable {
        @Override
        public void run() {
            System.out.println("当前客户端状态为:" + status);
            System.out.println(name + ":发送心跳");
            status = "UP";
        }
    }

    private class CacheRefreshThread implements Runnable {
        @Override
        public void run() {
            System.out.println(name + ":拉取注册表");
        }
    }
}
