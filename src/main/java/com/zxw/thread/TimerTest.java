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
//        test3();
//        test2();
        test1();
//        Thread.sleep(30000);
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

    /**
     * chapter45
     * “-XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -
     * XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -
     * XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log”
     */
    public static void test1() {
        byte[] arr = new byte[2 * 1024 * 1024];
        arr = new byte[2 * 1024 * 1024];
        arr = new byte[2 * 1024 * 1024];
        arr = null;
        byte[] arr2 = new byte[128 * 1024];
        byte[] arr3 = new byte[2 * 1024 * 1024];
        // 0.172: [GC (Allocation Failure) 0.170: [ParNew: 7735K->1024K(9216K), 0.0012498 secs] 7735K->3259K(19456K), 0.0014291 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
    }

    /**
     * 动态年龄规则
     */
    public static void test2() {
        byte[] arr = new byte[2 * 1024 * 1024];
        arr = new byte[2 * 1024 * 1024];
        arr = new byte[2 * 1024 * 1024];
        arr = null;
        byte[] arr2 = new byte[128 * 1024];
        byte[] arr3 = new byte[2 * 1024 * 1024];
        arr3 = new byte[2 * 1024 * 1024];
        arr3 = new byte[2 * 1024 * 1024];
        arr3 = new byte[128 * 1024];
        arr3 = null;
        byte[] arr4 = new byte[2 * 1024 * 1024];
        // 0.174: [GC (Allocation Failure) 0.174: [ParNew: 7899K->1024K(9216K), 0.0015212 secs] 7899K->3254K(19456K), 0.0017251 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        //0.176: [GC (Allocation Failure) 0.176: [ParNew: 7533K->379K(9216K), 0.0031924 secs] 9764K->5643K(19456K), 0.0032334 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        //0.180: [GC (CMS Initial Mark) [1 CMS-initial-mark: 5264K(10240K)] 7691K(19456K), 0.0001149 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        //0.180: [CMS-concurrent-mark-start]
    }

    public static void test3() {
        byte[] arr = new byte[6 * 1024 * 1024];
        arr = null;
        byte[] arr2 = new byte[2 * 1024 * 1024];
        byte[] arr3 = new byte[2 * 1024 * 1024];
//        byte[] arr6 = new byte[2 * 1024 * 1024];
        byte[] arr4 = new byte[128 * 1024];
        byte[] arr5 = new byte[2 * 1024 * 1024];
        // 0.172: [GC (Allocation Failure) 0.170: [ParNew: 7735K->1024K(9216K), 0.0012498 secs] 7735K->3259K(19456K), 0.0014291 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
    }
    public static void loadData() throws InterruptedException {
        byte[] data = null;
        for (int i = 0; i < 4; i++) {
            data = new byte[10 * 1024 * 1024];
        }
        data = null;
        byte[] arr = new byte[10 * 1024 * 1024];
        byte[] arr2 = new byte[10 * 1024 * 1024];
        byte[] arr3 = new byte[10 * 1024 * 1024];
        arr3 = new byte[10 * 1024 * 1024];
        Thread.sleep(1000);
    }
}
