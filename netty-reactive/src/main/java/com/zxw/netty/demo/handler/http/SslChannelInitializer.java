package com.zxw.netty.demo.handler.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * setHandshakeTimeout (long,TimeUnit)
 * setHandshakeTimeoutMillis (long)
 * getHandshakeTimeoutMillis()
 * 设置和获取超时时间，超时之后，握手
 * ChannelFuture 将会被通知失败
 * setCloseNotifyTimeout (long,TimeUnit)
 * setCloseNotifyTimeoutMillis (long)
 * getCloseNotifyTimeoutMillis()
 * 设置和获取超时时间，超时之后，将会触
 * 发一个关闭通知并关闭连接。这也将会导
 * 致通知该 ChannelFuture 失败
 * handshakeFuture() 返回一个在握手完成后将会得到通知的
 * ChannelFuture。如果握手先前已经执
 * 行过了，则返回一个包含了先前的握手结
 * 果的 ChannelFuture
 * close()
 * close(ChannelPromise)
 * close(ChannelHandlerContext,ChannelPromise)
 * 发送 close_notify 以请求关闭并销毁
 * 底层的 SslEngine
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {
    private final SslContext context;
    private final boolean startTls;

    /**
     * @param context
     * @param startTls 如果设置为 true，第一个写入的消息将不会被加密（客户端应该设置为 true）
     */
    public SslChannelInitializer(SslContext context,
                                 boolean startTls) {
        this.context = context;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        // 对于每个 SslHandler 实例，都使用 Channel 的 ByteBufAllocator 从 SslContext 获取一个新的 SSLEngine
        SSLEngine engine = context.newEngine(ch.alloc());
        // 将 SslHandler 作为第一个ChannelHandler 添加到ChannelPipeline 中
        ch.pipeline().addFirst("ssl",
                new SslHandler(engine, startTls));
    }
}