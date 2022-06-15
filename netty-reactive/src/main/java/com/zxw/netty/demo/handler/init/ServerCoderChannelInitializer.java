package com.zxw.netty.demo.handler.init;

import com.zxw.netty.demo.coder.define.MessageDecoder;
import com.zxw.netty.demo.coder.define.MessageEncoder;
import com.zxw.netty.demo.coder.length.LengthFieldDefineDecoder;
import com.zxw.netty.demo.coder.length.LengthServerHandler;
import com.zxw.netty.demo.coder.line.LineDefineDecoder;
import com.zxw.netty.demo.coder.line.LineServerHandler;
import com.zxw.netty.demo.coder.longtype.ByteToLongDecoder;
import com.zxw.netty.demo.coder.longtype.LongToByteEncoder;
import com.zxw.netty.demo.handler.http.HttpPipelineInitializer;
import com.zxw.netty.demo.handler.server.ServerStringInboundHandler;
import com.zxw.netty.demo.handler.server.ServerLongInboundHandler;
import com.zxw.netty.demo.handler.server.ServerMessageInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zxw
 * @date 2021/12/31 11:00
 */
public class ServerCoderChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        this.addLong(pipeline);
//        this.addMessage(pipeline);
//        this.addHttp(pipeline);
    }

    private void addLength(ChannelPipeline pipeline) {
        pipeline
                .addLast(new LengthFieldDefineDecoder(Integer.MAX_VALUE, 0, 2))
                .addLast(new LengthServerHandler());
    }

    private void addLine(ChannelPipeline pipeline) {
        pipeline
                .addLast(new LineDefineDecoder(10))
                .addLast(new LineServerHandler());
    }

    private void addMessage(ChannelPipeline pipeline) {
        pipeline
                .addLast(new MessageDecoder())
                .addLast(new MessageEncoder())
                .addLast(new ServerMessageInboundHandler());
    }

    private void addString(ChannelPipeline pipeline) {
        pipeline
                .addLast(new ServerStringInboundHandler());
    }

    private void addLong(ChannelPipeline pipeline) {
        pipeline
                .addLast(new LongToByteEncoder())
                .addLast(new ByteToLongDecoder())
                .addLast(new ServerLongInboundHandler());
    }

    private void addHttp(ChannelPipeline pipeline) {
        pipeline.addLast(new HttpPipelineInitializer(false));
    }
}
