package com.zxw.netty.demo.coder.charc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 这是你必须实现的唯一抽象方法。decode()方法被调用时将会传
 * 入一个包含了传入数据的 ByteBuf，以及一个用来添加解码消息
 * 的 List。对这个方法的调用将会重复进行，直到确定没有新的元
 * 素被添加到该 List，或者该 ByteBuf 中没有更多可读取的字节
 * 时为止。然后，如果该 List 不为空，那么它的内容将会被传递给
 * ChannelPipeline 中的下一个 ChannelInboundHandler
 *
 * 什么时候会用到解码器呢？很简单：每当需要为 ChannelPipeline 中的下一个 ChannelInboundHandler 转换入站数据时会用到。
 * 此外，得益于 ChannelPipeline 的设计，可以将多个解码器链接在一起，以实现任意复杂的转换逻辑，这也是 Netty 是如何支持代码的模块化以及复用的一个很好的例子。
 */
public class ByteToCharDecoder extends ByteToMessageDecoder {
    /**
     * 只要有字节可以被消费，这个方法就将会被调用。它将入站ByteBuf 转换为指定的消息格式，并将其转发给ChannelPipeline 中的下一个 ChannelInboundHandler
     */
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in,
                       List<Object> out) throws Exception {
        while (in.readableBytes() >= 2) {
            out.add(in.readChar());
        }
    }
}