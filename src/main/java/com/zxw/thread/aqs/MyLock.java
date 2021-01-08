package com.zxw.thread.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zxw
 * @date 2021-01-04 13:45
 */
public class MyLock implements Lock   {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        new Thread(() -> {
            lock.lock();
            System.out.println("获取到锁");
            while (true){

            }
        }).start();
        new Thread(() -> {
            lock.lock();
            System.out.println("获取到锁");
            while (true){

            }
        }).start();
    }

    private final Sync sync;

    public MyLock(Sync sync) {
        this.sync = new unFairSync();
    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    abstract static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryRelease(int arg) {
            return super.tryRelease(arg);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return super.tryAcquireShared(arg);
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            return super.tryReleaseShared(arg);
        }
    }

    static class FairSync extends Sync {
        @Override
        protected boolean tryAcquire(int arg) {
            return super.tryAcquire(arg);
        }
    }

    static class unFairSync extends Sync {
        @Override
        protected int tryAcquireShared(int arg) {
            return super.tryAcquireShared(arg);
        }
    }
}
