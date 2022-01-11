package com.zxw.netty.demo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class TestEmbeddedChannel {
    public static void main(String[] args) {
        ChannelInboundHandlerAdapter h1 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                ByteBuf buf = (ByteBuf) msg;
                log.info("接收到入站消息h1:{}", buf.toString(Charset.defaultCharset()));
                ByteBuf byteBuf = Unpooled.copiedBuffer("hello world", Charset.defaultCharset());
                super.channelRead(ctx, byteBuf);
            }
        };
        ChannelInboundHandlerAdapter h2 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                ByteBuf buf = (ByteBuf) msg;
                log.info("接收到入站消息h2:{}", buf.toString(Charset.defaultCharset()));
                super.channelRead(ctx, msg);
            }
        };
        ChannelOutboundHandlerAdapter h3 = new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                ByteBuf buf = (ByteBuf) msg;
                log.info("接收到出站消息h3:{}", buf.toString(Charset.defaultCharset()));
                super.write(ctx, msg, promise);
            }
        };
        ChannelOutboundHandlerAdapter h4 = new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                ByteBuf buf = (ByteBuf) msg;
                log.info("接收到出站消息h4:{}", buf.toString(Charset.defaultCharset()));
                super.write(ctx, msg, promise);
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel();
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("h1", h1);
        pipeline.addLast("h3", h3);
        pipeline.addLast("h2", h2);
        pipeline.addLast("h4", h4);
        // 模拟入站操作
        channel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("hello".getBytes()));
        // 模拟出站操作
        channel.writeOutbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("world".getBytes()));

    }
}