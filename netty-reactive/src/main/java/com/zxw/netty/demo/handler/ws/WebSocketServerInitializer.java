package com.zxw.netty.demo.handler.ws;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * example/src/main/java/io/netty/example/http/websocketx/client。
 * BinaryWebSocketFrame 数据帧：二进制数据
 * TextWebSocketFrame 数据帧：文本数据
 * ContinuationWebSocketFrame 数据帧：属于上一个 BinaryWebSocketFrame 或者 TextWebSocketFrame 的文本的或者二进制数据
 * CloseWebSocketFrame 控制帧：一个 CLOSE 请求、关闭的状态码以及关闭的原因
 * PingWebSocketFrame 控制帧：请求一个 PongWebSocketFrame
 * PongWebSocketFrame 控制帧：对 PingWebSocketFrame 请求的响应
 */
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(
                new HttpServerCodec(),
                // 为握手提供聚合的HttpRequest
                new HttpObjectAggregator(65536),
                // 如果被请求的端点是"/websocket"，则处理该升级握手
                new WebSocketServerProtocolHandler("/websocket"),
                // TextFrameHandler 处理TextWebSocketFrame
                new TextFrameHandler(),
                // BinaryFrameHandler 处理BinaryWebSocketFrame
                new BinaryFrameHandler(),
                new ContinuationFrameHandler());
    }

    public static final class TextFrameHandler extends
            SimpleChannelInboundHandler<TextWebSocketFrame> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx,
                                 TextWebSocketFrame msg) throws Exception {
// Handle text frame
        }
    }

    public static final class BinaryFrameHandler extends
            SimpleChannelInboundHandler<BinaryWebSocketFrame> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx,
                                 BinaryWebSocketFrame msg) throws Exception {
// Handle binary frame
        }
    }

    public static final class ContinuationFrameHandler extends
            SimpleChannelInboundHandler<ContinuationWebSocketFrame> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx,
                                 ContinuationWebSocketFrame msg) throws Exception {
// Handle continuation frame
        }
    }
}