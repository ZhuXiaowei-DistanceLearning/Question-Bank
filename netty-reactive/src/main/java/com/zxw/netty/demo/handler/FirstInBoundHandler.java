package com.zxw.netty.demo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author zxw
 * @date 2021/12/24 11:00
 */
@Slf4j
public class FirstInBoundHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        // 获取数据
        String message = msg.toString(Charset.defaultCharset());
        log.info("first接收到数据:{}", message);
    }
}
