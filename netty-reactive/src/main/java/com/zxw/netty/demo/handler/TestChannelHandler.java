package com.zxw.netty.demo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zxw
 * @date 2022/5/22 12:22
 */
@Slf4j
public class TestChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println(
                "Client received: " + byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 当把 ChannelHandler 添加到 ChannelPipeline 中时被调用
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
     * 当 Channel 处于活动状态时被调用；Channel 已经连接/绑定并且已经就绪
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("2.Channel 处于活动状态（已经连接到它的远程节点）。它现在可以接收和发送数据了");
        for (int i = 0; i < 10; i++) {
            log.info("写入数据:{}", i);
            ctx.writeAndFlush(Unpooled.copyLong(i));
        }
        super.channelActive(ctx);
    }

    /**
     * 当 Channel 离开活动状态并且不再连接它的远程节点时被调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("3.Channel 没有连接到远程节点");
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
        log.info("4.Channel 已经被创建，但还未注册到 EventLoop");
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
        log.info("channelReadComplete");
        super.channelReadComplete(ctx);
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

    /**
     * 当处理过程中在 ChannelPipeline 中有错误产生时被调用
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("exceptionCaught");
        super.exceptionCaught(ctx, cause);
    }
}
