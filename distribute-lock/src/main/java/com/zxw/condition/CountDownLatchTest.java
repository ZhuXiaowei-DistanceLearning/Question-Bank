package com.zxw.condition;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author zxw
 * @date 2022/5/31 17:19
 */
public class CountDownLatchTest {

    /**
     * CountDownLatch主要用来解决一个线程等待多个线程的场景
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("t1");
            countDownLatch.countDown();
        }, "t1").start();
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("t2");
            countDownLatch.countDown();
        }, "t2").start();
//        countDownLatch.await();
        System.out.println("等待");
        run();
    }

    static void run() throws InterruptedException {
        CountDownLatch doneSignal = new CountDownLatch(5);
        Executor e = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; ++i) // create and start threads
            e.execute(new WorkerRunnable(doneSignal, i));

        doneSignal.await();           // wait for all to finish
    }

    static class WorkerRunnable implements Runnable {
        private final CountDownLatch doneSignal;
        private final int i;

        WorkerRunnable(CountDownLatch doneSignal, int i) {
            this.doneSignal = doneSignal;
            this.i = i;
        }

        public void run() {
            try {
                doWork(i);
                doneSignal.countDown();
            } catch (Exception ex) {
            } // return;
        }

        void doWork(int i) {
            System.out.println(i);
        }
    }
}
