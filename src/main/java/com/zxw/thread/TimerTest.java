package com.zxw.thread;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Pattern;

/**
 * @author zxw
 * @date 2020/8/26 10:32
 */
public class TimerTest {
    static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("com.alibaba.nacos.client.naming.updater");
            t.setDaemon(true);
            return t;
        }
    });

    public static void main(String[] args) throws InterruptedException {
//        Thread.sleep(30000);
        boolean matches = Pattern.matches("[A-Za-z0-9_-]{1,50}", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(matches);
        System.out.println(Math.pow(10,2));
        System.out.println(1%7);
        System.out.println(2%7);
        System.out.println(3%7);
        System.out.println(9%7);
//        while (true){
//            loadData();
//        }
//        byte[] arr = new byte[4 * 1024 * 1024];
//        arr = new byte[4 * 1024 * 1024];
//        arr = new byte[4 * 1024 * 1024];
////        arr = null;
////        byte[] arr2 = new byte[1024 * 128];
//        byte[] arr3 = new byte[1024 * 1024 * 4];
//        arr3 = new byte[1024 * 1024 * 2];
//        arr3 = new byte[1024 * 1024 * 2];
//        arr3 = new byte[1024 * 128];
//        arr3 = null;
//        byte[] arr4 = new byte[1024 * 1024 * 2];
//        byte[] arr5 = new byte[1024 * 1024 * 2];
//        byte[] arr6 = new byte[1024 * 1024 * 2];
//        Timer timer = new Timer();
//        Timer timer2 = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("执行");
//            }
//        };
//        TimerTask task2 = new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("执行2");
//            }
//        };
//        // 固定时间的调度方式，延迟一秒，之后每隔一秒打印一次
//        // 打印结果如下：
//        // 11:03:55, called
//        // 11:03:56, called
//        // 11:03:57, called
//        // 11:03:58, called
//        // 11:03:59, called
//        // ...
//        timer.schedule(task, 1000, 1000);
//        timer2.schedule(task2, 1000, 1000);
    }

    public static void loadData() throws InterruptedException {
        byte[] data = null;
        for (int i = 0; i < 4; i++) {
            data = new byte[10*1024*1024];
        }
        data = null;
        byte[] arr = new byte[10 * 1024 * 1024];
        byte[] arr2 = new byte[10 * 1024 * 1024];
        byte[] arr3 = new byte[10 * 1024 * 1024];
        arr3 = new byte[10 * 1024 * 1024];
        Thread.sleep(1000);
    }
}
