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
    public static void main(String[] args) {
        EventLoopGroup group = new DefaultEventLoopGroup(2);
        TestRunner testRunner = new TestRunner();
        System.out.println((16 & -16) == 16);
        System.out.println((14 & -14));
        for (int i = 0; i < 10; i++) {
            EventLoop next = group.next();
            next.execute(testRunner);
            Promise<?> promise = new DefaultPromise<>(next);
            try {
                Object o = promise.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
//        group.shutdownGracefully();
    }

    public static class TestRunner implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("测试线程数据");
        }
    }
}
