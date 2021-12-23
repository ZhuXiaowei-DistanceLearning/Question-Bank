package com.zxw.netty.demo.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zxw
 * @date 2021/12/23 14:10
 */
@Slf4j
public class LongDecoder extends ByteToMessageDecoder {
    /**
     * 需要注意的是: 在解码的过程中，一定要判断解码的可读字节是否足够，否则的话可能产生问题
     * 需要读取一个Long类型的数据，那么在读取之前需要判断当前的ByteBuf中是否还有8个字节，否则的话，则不予处理。
     * 在某些场景下，可能需要使用decodeLast(ChannelHandleContext,ByteBuf,List) 方法，这个方法在Channel状态变为非活动的时候，会被调用一次，重写该方法可以提供一些特殊的处理，比如在产生一个LastHttpContent。
     * @param ctx  表示的是当前处理器的上下文，包含该处理链
     * @param in 值得是Netty为我么自动将Byte转换出来的ByteBuf对象，我们只要从这个对象中读取数据即可.
     * @param out list传递的是一个Object 集合,当前ByteBuf 中数据已经读取完成的时候，若list集合的数据不为空，则解码器会把List集合的数据交个下一个处理器操作。
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("调用解码器的decode方法");
        System.out.println("ByteBuf可读字节数:" + in.readableBytes());
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
