//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zxw.netty.demo.handler.ws.xs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import java.util.Locale;

public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    public WebSocketFrameHandler() {
    }

    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        String message;
        if (frame instanceof TextWebSocketFrame) {
            message = ((TextWebSocketFrame)frame).text();
            ctx.channel().writeAndFlush(new TextWebSocketFrame(message.toUpperCase(Locale.US)));
        } else {
            message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }
}
