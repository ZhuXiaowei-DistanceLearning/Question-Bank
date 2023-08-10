package com.zxw.thread;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestBlocked {

    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Thread t1 = new Thread(() -> {
            lock.writeLock().lock();
            try {
                System.out.println("t1线程获取到写锁");
                Thread.sleep(30000); // 模拟写操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
                System.out.println("t1线程释放写锁");
            }
        }, "t1线程");
        t1.start();

        Thread.sleep(100); // 确保t1线程先执行

        Thread t2 = new Thread(() -> {
            lock.readLock().lock();
            try {
                System.out.println("t2线程获取到读锁");
                Thread.sleep(10000); // 模拟读操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
                System.out.println("t2线程释放读锁");
            }
        }, "t2线程");
        t2.start();

        Thread.sleep(100); // 确保t2线程已经开始运行
        System.out.println("t2线程的状态是：" + t2.getState()); // 应该是BLOCKED
    }
}