package com.zxw.condition;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author zxw
 * @date 2022/5/31 17:37
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier countDownLatch = new CyclicBarrier(2, () -> {
            System.out.println("执行完毕");
        });
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println("t1");
        }, "t1").start();
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println("t2");
        }, "t2").start();
        System.out.println("等待");
    }
}
