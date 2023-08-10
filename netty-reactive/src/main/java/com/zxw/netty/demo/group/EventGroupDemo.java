package com.zxw.netty.demo.group;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author zxw
 * @date 2022-01-03 21:24
 */
@Slf4j
public class EventGroupDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoopGroup group = new DefaultEventLoopGroup();
        System.out.println((16 & -16) == 16);
        System.out.println((14 & -14));
        for (int i = 0; i < 10; i++) {
            // 运行任务来处理在连接的生命周期内发生的事件是任何网络框架的基本功能
            EventLoop next = group.next();
            Promise<?> promise = new DefaultPromise<>(next);
            TestRunner testRunner = new TestRunner(promise);
            next.execute(testRunner);
            log.info("now:{}", promise.getNow()); // 还没有结果
            log.info("get:{}", promise.get());

        }
//        group.shutdownGracefully();
    }

    public static class TestRunner implements Runnable {
        Promise promise;

        public TestRunner(Promise promise) {
            this.promise = promise;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("测试线程数据");
            promise.setSuccess("10231");
        }
    }
}
