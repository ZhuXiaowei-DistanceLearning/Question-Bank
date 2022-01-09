package com.zxw.netty.demo.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * encode()方法是你需要实现的唯一抽象方法。它被调用时
 * 将会传入要被该类编码为 ByteBuf 的（类型为 I 的）出站
 * 消息。该 ByteBuf 随后将会被转发给 ChannelPipeline
 * 中的下一个 ChannelOutboundHandler
 */
public class CharToByteEncoder extends
        MessageToByteEncoder<Character> {
    @Override
    public void encode(ChannelHandlerContext ctx, Character msg,
                       ByteBuf out) throws Exception {
        out.writeChar(msg);
    }
}