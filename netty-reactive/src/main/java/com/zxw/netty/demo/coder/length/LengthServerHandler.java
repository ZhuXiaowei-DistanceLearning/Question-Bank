package com.zxw.netty.demo.coder.length;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author zxw
 * @date 2022-01-09 17:32
 */
@Data
@Slf4j
public class LengthServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("接收到的分隔符消息:{}", msg.toString(Charset.defaultCharset()));
    }
}
