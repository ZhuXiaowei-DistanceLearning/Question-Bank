package com.zxw.netty.demo.handler.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Netty 为压缩和解压缩提供了 ChannelHandler 实现，它们同时支持 gzip 和 deflate 编 码。
 * 客户端可以通过提供以下头部信息来指示服务器它所支持的压缩格式：
 * GET /encrypted-area HTTP/1.1
 * Host: www.example.com
 * Accept-Encoding: gzip, deflate
 * 然而，需要注意的是，服务器没有义务压缩它所发送的数据
 */
public class HttpCompressionInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;

    public HttpCompressionInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (isClient) {
            pipeline.addLast("codec", new HttpClientCodec());
            // 如果是客户端，则添加HttpContentDecompressor 以处理来自服务器的压缩内容
            pipeline.addLast("decompressor",
                    new HttpContentDecompressor());
        } else {
            // 如果是服务器，则添加 HttpServerCodec
            pipeline.addLast("codec", new HttpServerCodec());
            // 如果是服务器，则添加HttpContentCompressor来压缩数据（如果客户端支持它）
            pipeline.addLast("compressor",
                    new HttpContentCompressor());
        }
    }
}