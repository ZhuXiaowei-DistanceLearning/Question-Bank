package com.zxw.condition;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zxw
 * @date 2022/6/4 16:22
 */
public class RateTest {
    public static void main(String[] args) {
        //限流器流速：2个请求/秒
        RateLimiter limiter =
                RateLimiter.create(2.0);
        //执行任务的线程池
        ExecutorService es = Executors
                .newFixedThreadPool(1);
        //记录上一次执行时间
        var ref = new Object() {
            Long prev = System.nanoTime();
        };
        //测试执行20次
        for (int i = 0; i < 20; i++) {
            //限流器限流
            limiter.acquire();
            //提交任务异步执行
            es.execute(() -> {
                long cur = System.nanoTime();
                //打印时间间隔：毫秒
                System.out.println(
                        (cur - ref.prev) / 1000_000);
                ref.prev = cur;
            });
        }
    }
}
