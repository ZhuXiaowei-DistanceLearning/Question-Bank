package com.zxw.condition;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("All workers finished their jobs");
        });

        Thread t1 = new Thread(new Worker(cyclicBarrier), "Worker-1");
        Thread t2 = new Thread(new Worker(cyclicBarrier), "Worker-2");
        Thread t3 = new Thread(new Worker(cyclicBarrier), "Worker-3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class Worker implements Runnable {
    private CyclicBarrier cyclicBarrier;

    public Worker(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is doing its job");
        try {
            Thread.sleep(1000);
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " finished its job");
    }
}
