package com.zxw.netty.demo.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author zxw
 * @date 2021/12/30 22:51
 */
public class MessageEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out)
            throws Exception {
        System.out.println("Starting encode message");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}