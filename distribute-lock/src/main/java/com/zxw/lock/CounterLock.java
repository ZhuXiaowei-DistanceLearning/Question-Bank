package com.zxw.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zxw
 * @date 2022-02-02 19:46
 */
@Slf4j
public class CounterLock {
    AtomicInteger count = new AtomicInteger(0);

    long minute = 60 * 1000L;

    long startTime = 0L;

    public static void main(String[] args) {
        CounterLock counterLock = new CounterLock();
        ExecutorService executorService = Executors.newFixedThreadPool(10, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        while (true) {
            executorService.execute(() -> {
                counterLock.lock();
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void lock() {
        long currentTimeMillis = System.currentTimeMillis();
        if (startTime == 0L) {
            startTime = currentTimeMillis;
        }
        if (currentTimeMillis - startTime < minute) {
            int i = incr();
            if (i > 100) {
                log.info("限流了,剩余{}s重新计数", i, 60 - (currentTimeMillis - startTime) / 1000);
            } else {
                log.info("当前请求剩余次数：{}", i);
            }
        } else {
            startTime = 0L;
            count.set(0);
        }
    }

    private int incr() {
        for (; ; ) {
            int current = count.get();
            int next = count.get() + 1;
            if (count.compareAndSet(current, next))
                return next;
        }
    }
}
