package com.zxw.netty.demo.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class IntegerEncoder extends MessageToByteEncoder<Integer> {
  @Override
  protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out) throws Exception {
    out.writeInt(msg);
  }
}