package com.zxw.rpc.client;

import com.zxw.netty.demo.handler.init.ClientCoderChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zxw
 * @date 2022/6/15 14:40
 */
public class RpcBootStrap {

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 2077;


    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootStrap = new Bootstrap();
            bootStrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
//                    .handler(new ChannelInitializer<>() {
//                        @Override
//                        protected void initChannel(Channel ch) throws Exception {
//                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));
//                            pipeline.addLast(new LongToByteEncoder());
//                        }
//                    });
                    .handler(new ClientCoderChannelInitializer());
            // 连接到远程节点，阻塞等待直到连接完成
            ChannelFuture f = bootStrap.connect(HOST, PORT).sync();
            //阻塞，直到Channel 关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
