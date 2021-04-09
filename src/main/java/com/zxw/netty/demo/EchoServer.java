package com.zxw.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import javax.sound.sampled.Port;
import java.net.InetSocketAddress;

/**
 * Netty:
 *  BootStrap:启动引导类
 *  Channel:执行网络io操作
 *  Selector:轮询io
 *  NioEventLoop：维护了一个线程和队列，执行I/O和非I/O任务
 *  NioEventLoopGroup:管理NioEventLoop
 *  ChannelHandler：处理I/O事件或拦截I/O操作并将其转发到其 ChannelPipeline(业务处理链)中的下一个处理程序。
 *  ChannelHandlerContext：保存 Channel 相关的所有上下文信息，同时关联一个 ChannelHandler 对象。
 *  ChannelPipline:保存 ChannelHandler 的 List，用于处理或拦截 Channel 的入站事件和出站操作。
 *
 * @author zxw
 * @date 2020-05-12 15:15:19
 * @Description
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        EchoServer echoServer = new EchoServer(10010);
        final EchoServerHandler echoServerHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建EventLoopGroup
            // 创建Server-Bootstrap
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    // 执行所使用的的NIO传输channel
                    .channel(NioServerSocketChannel.class)
                    // 使用指定的端口设置套接字
                    .localAddress(new InetSocketAddress(10010))
                    // 添加一个channelHandler到字Channel的ChannelPipeline
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(echoServerHandler);
                        }
                    });
            ChannelFuture f = bootstrap.bind().sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
