package com.zxw.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zxw
 * @date 2022/5/31 15:32
 */
public class ConditionTest {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1000);
                System.out.println("准备休眠");
                condition.await();
                System.out.println("释放condition");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            lock.unlock();
        }, "T1").start();
        new Thread(() -> {
            lock.lock();
            System.out.println("唤醒condition");
            condition.signal();
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }, "T2").start();
    }
}
