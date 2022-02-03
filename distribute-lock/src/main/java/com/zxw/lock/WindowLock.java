package com.zxw.lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zxw
 * @date 2022-02-02 23:39
 */
@Slf4j
public class WindowLock {
    private int capacity = 6;
    private long interval = 60 * 1000L / capacity;
    private LinkedList<WindowSlot> window = new LinkedList();
    private volatile int count = 0;
    private volatile int slotNum = 0;
    private volatile long lastSlotTime = 0L;
    private ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        WindowLock windowLock = new WindowLock();
        executorService.execute(() -> {
            while (true){
                windowLock.lock.lock();
                for (WindowSlot windowSlot : windowLock.window) {
                    log.info("当前{},开始时间:{},数量:{},总请求数量:{}", windowSlot.getName(), new Date(windowSlot.getStartTime()), windowSlot.getCount(), windowLock.count);
                }
                windowLock.lock.unlock();
                log.info("------------");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        while (true) {
            executorService.execute(() -> {
                try {
                    windowLock.lock();
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }

    public synchronized void lock() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastSlotTime > interval) {
            WindowSlot next = new WindowSlot((slotNum % capacity + 1) + "号槽", System.currentTimeMillis(), 0);
            lock.lock();
            window.add(next);
            lock.unlock();
            slotNum++;
            lastSlotTime = next.startTime;
        }
        WindowSlot last = window.peekLast();
        if (count >= 100) {
            log.error("限流了");
        } else {
            last.count++;
            count++;
        }
        if (window.size() > capacity) {
            lock.lock();
            WindowSlot windowSlot = window.pollFirst();
            lock.unlock();
            count -= windowSlot.getCount();
        }
    }

    @Data
    @AllArgsConstructor
    class WindowSlot {
        private String name;
        private long startTime;
        private int count;
    }
}
