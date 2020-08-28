package com.zxw.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * @author zxw
 * @date 2020/8/25 22:36
 */
public class DiscoveryClientTest {
    static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("com.alibaba.nacos.client.naming.updater");
            t.setDaemon(true);
            return t;
        }
    });

    public static void main(String[] args) {
        System.out.println(TimeUnit.SECONDS.toMillis(30));
        DiscoveryClient d1 = new DiscoveryClient("d1");
        DiscoveryClient d2 = new DiscoveryClient("d2");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        scheduler.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("run");
            }
        }, 0, 5, TimeUnit.SECONDS);
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    Thread.sleep(1000);
                    System.out.println(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        System.out.println("主线程结束");
//        while (true){
//
//        }
    }
}
