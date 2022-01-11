package com.zxw.netty.demo.handler.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author zxw
 * @date 2022/1/11 9:06
 */
@Slf4j
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpMessage> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpMessage msg) throws Exception {
        HttpHeaders headers = msg.headers();
        log.info("请求头:{}\n", headers);
        Thread.sleep(5000);
        // 创建http响应
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer("success", Charset.defaultCharset()));
        // 设置头信息
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        log.info("响应结束");
    }
}
