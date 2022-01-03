package com.zxw.netty.demo.group;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zxw
 * @date 2022-01-03 21:24
 */
@Slf4j
public class EventGroupDemo {
    public static void main(String[] args) {
        EventLoopGroup group = new DefaultEventLoopGroup(1);
        EventLoop next = group.next();
        TestRunner testRunner = new TestRunner();
        next.execute(testRunner);
        group.shutdownGracefully();
    }

    public static class TestRunner implements Runnable{

        @Override
        public void run() {
            log.info("测试线程数据");
        }
    }
}
