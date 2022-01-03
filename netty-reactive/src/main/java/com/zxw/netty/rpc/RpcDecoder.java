package com.zxw.netty.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RpcDecoder<T> extends ByteToMessageDecoder {

  @Override
  public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
    log.trace("执行解码:{}", in);
    // 如果不够一个整数，那么继续等待
    if (in.readableBytes() < 4) {
      return;
    }

    // 够一个整数之后，读取这个整数
    in.markReaderIndex();
    int dataLength = in.readInt();

    // 获得内容的长度之后，判断字节是否足够
    if (in.readableBytes() < dataLength) {
      in.resetReaderIndex();
      return;
    }

    // 读取数据
    byte[] data = new byte[dataLength];
    in.readBytes(data);

    // 反序列化
//    T obj = SerializationUtil.deserialize(data, aClass);
//    out.add(obj);
//    log.trace("解码完成:{}", obj);
  }
}