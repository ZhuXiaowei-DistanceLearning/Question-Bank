package com.zxw.netty.demo.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("Starting Decode message");
        int len = in.readableBytes();
        byte[] content = new byte[len];
        in.readBytes(content);
        MessageProtocol msg = new MessageProtocol();
        msg.setLen(len);
        msg.setContent(content);
        out.add(msg);
    }
}