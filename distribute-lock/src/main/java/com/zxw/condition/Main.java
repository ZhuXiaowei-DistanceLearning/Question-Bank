package com.zxw.condition;

import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int n = 3;
        CountDownLatch countDownLatch = new CountDownLatch(n);
        for (int i = 0; i < n; i++) {
            new Thread(new Task(countDownLatch)).start();
        }
        countDownLatch.await();
        System.out.println("所有线程执行完毕！");
    }

    static class Task implements Runnable {
        private CountDownLatch countDownLatch;

        public Task(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " 开始执行任务");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " 任务执行完毕");
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
