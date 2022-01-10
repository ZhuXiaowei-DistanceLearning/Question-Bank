package com.zxw.netty.demo.encode.longtype;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ByteToLongDecoder extends ByteToMessageDecoder {
    /**
     * decode 方法会根据接收到的数据，被调用多次，知道确定没有新的元素被添加到list，或者是ByteBuf 没有更多的可读字节为止
     * 如果 list out不为空，就会将list的内容传递给下一个 Handler 进行处理，该处理器的方法也会被调用多次。
     * @param ctx 上下文对象
     * @param in 入栈的 ByteBuf
     * @param out list集合，将解码后的数据传给下一个Handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //因为long为8个字节，所以需要8个字节才能读取成一个long类型的数据
        if (in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
