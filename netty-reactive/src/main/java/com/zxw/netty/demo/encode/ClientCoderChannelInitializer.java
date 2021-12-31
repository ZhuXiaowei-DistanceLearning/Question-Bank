package com.zxw.netty.demo.encode;

import com.zxw.netty.demo.ClientInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zxw
 * @date 2021/12/31 11:00
 */
public class ClientCoderChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline
//                .addLast(new LongToByteEncoder())
//                .addLast(new ByteToLongDecoder())
                .addLast(new ClientInboundHandler());
    }
}
