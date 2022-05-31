package com.zxw.condition;

import java.util.concurrent.locks.StampedLock;

/**
 * @author zxw
 * @date 2022/5/31 16:21
 */
public class StampedLockTest {
    static final StampedLock sl = new StampedLock();

    /**
     * StampedLock支持乐观读的方式。
     * ReadWriteLock支持多个线程同时读，但是当多个线程同时读的时候，所有的写操作会被阻塞；
     * 而StampedLock提供的乐观读，是允许一个线程获取写锁的，也就是说不是所有的写操作都被阻塞。
     *
     * @param args
     */
    public static void main(String[] args) {
        // 获取/释放悲观读锁示意代码
        long stamp = sl.readLock();
        try {
            //省略业务相关代码
        } finally {
            sl.unlockRead(stamp);
        }
        // 获取/释放写锁示意代码
        stamp = sl.writeLock();
        try {
            //省略业务相关代码
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    void distanceFromOrigin() {
        // 乐观读
        long stamp =
                sl.tryOptimisticRead();
        // 读入局部变量，
        // 读的过程数据可能被修改
        int curX = 1, curY = 1;
        //判断执行读操作期间，
        //是否存在写操作，如果存在，
        //则sl.validate返回false
        if (!sl.validate(stamp)) {
            // 升级为悲观读锁
            stamp = sl.readLock();
            try {
                curX = 2;
                curY = 2;
            } finally {
                //释放悲观读锁
                sl.unlockRead(stamp);
            }
        }
    }
}
