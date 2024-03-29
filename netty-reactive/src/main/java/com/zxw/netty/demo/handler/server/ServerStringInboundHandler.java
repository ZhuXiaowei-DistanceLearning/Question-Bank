/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.zxw.netty.demo.handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Handler implementation for the echo server.
 */
@Sharable
@Slf4j
public class ServerStringInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {

    int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        String message = new String(bytes, "utf-8");
        log.info("服务器端接收到消息:{}", message);
        log.info("服务器端接收到消息量" + (++this.count));
        ByteBuf response = Unpooled.copiedBuffer(UUID.randomUUID().toString() + "--", Charset.defaultCharset());
        ctx.writeAndFlush(response);
    }

    /**
     * 当把 ChannelHandler 添加到 ChannelPipeline 中时被调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("处理器添加");
        super.handlerAdded(ctx);
    }

    /**
     * 当从 ChannelPipeline 中移除 ChannelHandler 时被调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("处理器移除");
        super.handlerRemoved(ctx);
    }

    /**
     * Channel 已经注册到它的 EventLoop 并且能够处理 I/O 时被调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("1.channel被注册到EventLoop");
        super.channelRegistered(ctx);
    }

    /**
     * 当 Channel 离开活动状态并且不再连接它的远程节点时被调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("4.Channel 没有连接到远程节点");
        super.channelInactive(ctx);
    }

    /**
     * Channel 从它的 EventLoop 注销并且无法处理任何 I/O 时被调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("5.Channel 已经被创建，但还未注册到 EventLoop");
        super.channelUnregistered(ctx);
    }

    /**
     * 当Channel上的一个读操作完成时被调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("3.channelReadComplete");
        super.channelReadComplete(ctx);
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 当 ChannelnboundHandler.fireUserEventTriggered()方法被调
     * 用时被调用，因为一个 POJO 被传经了 ChannelPipeline
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("userEventTriggered");
        super.userEventTriggered(ctx, evt);
    }

    /**
     * 当 Channel 的可写状态发生改变时被调用。用户可以确保写操作不会完成
     * 得太快（以避免发生 OutOfMemoryError）或者可以在 Channel 变为再
     * 次可写时恢复写入。可以通过调用 Channel 的 isWritable()方法来检测
     * Channel 的可写性。与可写性相关的阈值可以通过 Channel.config().
     * setWriteHighWaterMark()和 Channel.config().setWriteLowWaterMark()方法来设置
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        log.info("channelWritabilityChanged");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
