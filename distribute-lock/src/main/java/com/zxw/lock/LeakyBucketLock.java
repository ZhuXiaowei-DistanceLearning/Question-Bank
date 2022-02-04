package com.zxw.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zxw
 * @date 2022-02-03 21:43
 */
@Slf4j
public class LeakyBucketLock {
    private long rate;
    private long water;
    private long capacity;
    private long timestamp;

    public static void main(String[] args) {


    }

    public void lock() {
        long currentTimeMillis = System.currentTimeMillis();
        timestamp = currentTimeMillis;
        water = Math.max(0, water - ((currentTimeMillis - timestamp) / 1000) * rate);
        if ((water + 1) < capacity) {

        } else {
            log.info("限流了");
        }
    }
}
