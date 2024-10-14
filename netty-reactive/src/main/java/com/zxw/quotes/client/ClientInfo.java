package com.zxw.quotes.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zxw
 * @date 2024-05-23 10:03
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ClientInfo {

    /**
     * ip
     */
    String ip;

    /**
     * 端口
     */
    int port;

    /**
     * 名称
     */
    String name;

    /**
     * 上下文
     */
    ChannelHandlerContext ctx;

    /**
     * 首次连接时间
     */
    long connectTime = System.currentTimeMillis();

    /**
     * 上次连接时间
     */
    long lastConnectTime = 0;

    /**
     * 连接次数
     */
    AtomicLong connectNum = new AtomicLong(1);

    public void sendMessage(ByteBuf msg) {
        Channel channel = ctx.channel();
        if (!channel.isActive()) {
            log.error("关闭通道 --- ip:[{}] --- name:[{}] --- 激活状态:[{}]--- 写状态:[{}]", ip, name, channel.isActive(), channel.isWritable());
            channel.close();
        }
        if (channel.isWritable()) {
            // 检查当前线程是否是EventLoop线程
            if (ctx.channel().eventLoop().inEventLoop()) {
                // 当前线程是EventLoop线程，直接发送消息
                ctx.writeAndFlush(msg);
            } else {
                // 当前线程不是EventLoop线程，将发送操作提交到EventLoop
               ctx.channel().eventLoop().execute(() -> ctx.writeAndFlush(msg));
            }
        } else {
            log.error("channel:[{}]通道不可写", channel);
        }
    }
}
