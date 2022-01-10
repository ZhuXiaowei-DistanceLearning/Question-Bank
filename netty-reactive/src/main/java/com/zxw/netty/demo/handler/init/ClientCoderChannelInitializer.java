package com.zxw.netty.demo.handler.init;

import com.zxw.netty.demo.encode.MessageDecoder;
import com.zxw.netty.demo.encode.MessageEncoder;
import com.zxw.netty.demo.encode.longtype.ByteToLongDecoder;
import com.zxw.netty.demo.encode.longtype.LongToByteEncoder;
import com.zxw.netty.demo.handler.client.LongInBoundHandler;
import com.zxw.netty.demo.handler.client.ClientMessageInboundHandler;
import com.zxw.netty.demo.handler.http.HttpPipelineInitializer;
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
//        this.addHttp(pipeline);
        this.addLong(pipeline);
    }

    private void addMessage(ChannelPipeline pipeline) {
        pipeline
                .addLast(new MessageDecoder())
                .addLast(new MessageEncoder())
                .addLast(new ClientMessageInboundHandler());
    }

    private void addLong(ChannelPipeline pipeline) {
        pipeline
                .addLast(new ByteToLongDecoder())
                .addLast(new LongToByteEncoder())
                .addLast(new LongInBoundHandler());
    }

    public void addHttp(ChannelPipeline pipeline) {
        pipeline.addLast(new HttpPipelineInitializer(true));
    }
}