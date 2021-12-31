package com.zxw.netty.demo.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zxw
 * @date 2021/12/30 22:51
 */
@Slf4j
public class MessageEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out)
            throws Exception {
        log.info("消息转换字节码");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}