package com.zxw.condition;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zxw
 * @date 2022/12/1 10:00
 */
public class ReenReadWriteLock {
    Object data;
    boolean cacheValid;
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args) throws InterruptedException {
        RWDictionary rwDictionary = new RWDictionary();
        rwDictionary.put("a", "a");
        Thread t1 = new Thread(() -> {
            while (true) {
                String a = rwDictionary.get("a");
                System.out.println(a);
            }
        }, "t1");
        t1.start();
//        Thread t2 = new Thread(() -> {
//            while (true) {
//                String a = rwDictionary.get("a");
//                System.out.println(a);
//            }
//        }, "t2");
//        t2.start();
        Thread t3 = new Thread(() -> {
            while (true) {
                rwDictionary.put("a", "b");
            }
        }, "t3");
        t3.start();
        Thread.currentThread().join();
    }

    void processCachedData() {
        rwl.readLock().lock();
        if (!cacheValid) {        // Must release read lock before acquiring write lock
            rwl.readLock().unlock();
            rwl.writeLock().lock();
            try {          // Recheck state because another thread might have          // acquired write lock and changed state before we did.
                if (!cacheValid) {
                    data = new Object();
                    cacheValid = true;
                }          // Downgrade by acquiring read lock before releasing write lock
                rwl.readLock().lock();
            } finally {
                rwl.writeLock().unlock(); // Unlock write, still hold read
            }
        }
        try {
//            use(data);
        } finally {
            rwl.readLock().unlock();
        }
    }
}
