package com.zxw.interview;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zxw
 * @date 2020-12-27 19:23
 */
public class TestThread {
    static final Object monitor = new Object();
    static AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) {
//        threadState();
        threadPool();
    }
    public static void threadPool(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(()->{
            System.out.println("111");
        });
        System.out.println(executorService.isTerminated());
    }
    public static void threadState() {
        try {
            for (int j = 0; j < 10; j++) {
                new Thread(() -> {
                    boolean isYield = true;
                    long l = System.currentTimeMillis();
                    for (int i = 0; i < 1000; i++) {
                        if (isYield) Thread.yield();
                    }
                    System.out.println("线程编号：" + Thread.currentThread().getName() + " 执行完成耗时：" + (System.currentTimeMillis() - l) + " (毫秒)" + (isYield ? "让出CPU----------------------" : "不让CPU"));
                }).start();
            }
            // new
            Thread thread = new Thread();
            System.out.println("当前线程状态new:" + thread.getState());
            // run
            new Thread(() -> {
                System.out.println("当前线程状态run:" + Thread.currentThread().getState());
            }).start();
            // block
            Thread block = new Thread(() -> {
                synchronized (monitor) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread block2 = new Thread(() -> {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            // wait
            Thread wait = new Thread(() -> {
                try {
                    Thread.currentThread().join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            wait.start();
//            block.start();
            block2.start();
            Thread.sleep(1000);
            wait.join();
            System.out.println("当前线程状态block:" + block.getState());
            System.out.println("当前线程状态block2:" + block2.getState());
            System.out.println("当前线程状态wait:" + wait.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
