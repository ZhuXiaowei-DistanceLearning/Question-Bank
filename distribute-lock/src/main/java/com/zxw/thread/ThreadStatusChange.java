package com.zxw.thread;

import cn.hutool.core.util.StrUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zxw
 * @date 2022/11/29 14:53
 */
public class ThreadStatusChange {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Lock lock = new ReentrantLock();
        Object o = new Object();
        Thread t1 = new Thread(() -> {
            System.out.println("t1线程进入sleep方法，状态变更为TIMED_WAITING");
            try {
                Thread.sleep(2000);
                System.out.println("t1线程进入ReentrantLock锁方法，状态变更为WAITING");
                lock.lock();
                Thread.sleep(2000);
                lock.unlock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("t1线程进入synchronized锁方法，状态变更为Block");
            synchronized (o) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("t1线程进入LockSupport锁方法，状态变更为WAITING");
            LockSupport.parkUntil(System.currentTimeMillis() + 1000);

        }, "t1");
        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
            synchronized (o) {
                System.out.println("获取到锁");
            }
        }, "t2");
        Thread t3 = new Thread(() -> {
          while (true){
              try {
                  String take = queue.take();
              } catch (InterruptedException e) {
                  throw new RuntimeException(e);
              }
          }
        }, "t3");
        t2.start();
        printThread(t1);
        t1.start();
        t3.start();
        printThread(t3);
        printThread(t1);
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                printThread(t1);
                printThread(t3);
            }
        }, "monitor").start();
        Thread.currentThread().join();
    }

    public static void printThread(Thread t) {
        System.out.println(StrUtil.format("当前线程:{}，运行状态:{}", t.getName(), t.getState()));
    }

}
