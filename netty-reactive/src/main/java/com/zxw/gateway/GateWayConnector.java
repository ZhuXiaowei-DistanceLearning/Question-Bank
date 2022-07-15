package com.zxw.gateway;

import com.zxw.gateway.handler.HttpHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author zxw
 * @date 2022/7/15 16:28
 */
public class GateWayConnector implements Runnable {

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture f = serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
//                            p.addLast(new HttpServerCodec());
//                            p.addLast(new HttpServerExpectContinueHandler());
                            p.addLast(new HttpRequestDecoder());
                            // Uncomment the following line if you don't want to handle HttpChunks.
                            //p.addLast(new HttpObjectAggregator(1048576));
                            p.addLast(new HttpResponseEncoder());
                            // Remove the following line if you don't want automatic content compression.
                            //p.addLast(new HttpContentCompressor());
                            p.addLast(new HttpHandler());
                        }
                    })
                    .bind(8080)
                    .sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
