package com.zxw.netty.demo.handler.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zxw
 * @date 2022/1/11 14:27
 */
@Slf4j
public class HttpServerOutHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("出站消息");
        super.write(ctx, msg, promise);
    }
}
