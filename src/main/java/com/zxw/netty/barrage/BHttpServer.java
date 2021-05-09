package com.zxw.netty.barrage;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;

/**
 * @author zxw
 * @date 2021-04-20 21:47
 */
public class BHttpServer {
    public void openServer(int port) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class);
        EventLoopGroup boot = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup(8);
        bootstrap.group(boot, work);
        bootstrap.childHandler(new ChannelInitializer<>() {

            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                channel.pipeline().addLast("http-encode",new HttpRequestEncoder());
            }
        });
    }
}
