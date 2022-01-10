package com.zxw.netty.demo.handler.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.example.http.file.HttpStaticFileServerHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * HttpRequestEncoder 将HttpRequest、HttpContent 和 LastHttpContent 消息编码为字节
 * HttpResponseEncoder 将HttpResponse、HttpContent 和LastHttpContent 消息编码为字节
 * HttpRequestDecoder 将字节解码为HttpRequest、HttpContent 和 LastHttpContent 消息
 * HttpResponseDecoder 将字节解码为HttpResponse、HttpContent 和LastHttpContent 消息
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {
    private final boolean client;

    public HttpPipelineInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (client) {
            pipeline.addLast("decoder", new HttpResponseDecoder());
            pipeline.addLast("encoder", new HttpRequestEncoder());
        } else {
            pipeline.addLast("decoder", new HttpRequestDecoder());
            pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
            pipeline.addLast("encoder", new HttpResponseEncoder());
            pipeline.addLast("chunked", new ChunkedWriteHandler());
            pipeline.addLast("file", new HttpStaticFileServerHandler());
        }
    }
}