package com.zxw.utils;

import io.netty.util.HashedWheelTimer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author zxw
 * @date 2022/8/15 16:56
 */
@Slf4j
public class DelayUtils {
    public static HashedWheelTimer timer = new HashedWheelTimer();

    public static void push(Runnable runnable) {
        timer.newTimeout(timeout -> {
            runnable.run();
        }, 10, TimeUnit.SECONDS);
    }

    public static void push(Runnable runnable, int delay, TimeUnit timeUnit) {
        timer.newTimeout(timeout -> {
            runnable.run();
        }, delay, timeUnit);
    }
}
