package com.zxw.netty.demo.handler;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.epoll.EpollSocketChannel;

/**
 * @author zxw
 * @date 2021/12/24 9:55
 */
public class ChannelDemo {
    public static void main(String[] args) {
        test1();
    }
    public static void test1() {
        EpollSocketChannel ch = new EpollSocketChannel();
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new FirstInBoundHandler());
        pipeline.addLast(new SecondInBoundHandler());
        pipeline.addLast(new ThirdInBoundHandler());
        pipeline.fireChannelRead("qwe");
    }

}
