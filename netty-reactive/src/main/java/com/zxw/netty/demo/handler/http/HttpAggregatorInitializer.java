package com.zxw.netty.demo.handler.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 在 ChannelInitializer 将 ChannelHandler 安装到 ChannelPipeline 中之后，你
 * 便可以处理不同类型的 HttpObject 消息了。但是由于 HTTP 的请求和响应可能由许多部分组
 * 成，因此你需要聚合它们以形成完整的消息。为了消除这项繁琐的任务，Netty 提供了一个聚合
 * 器，它可以将多个消息部分合并为 FullHttpRequest 或者 FullHttpResponse 消息。通过
 * 这样的方式，你将总是看到完整的消息内容。
 * 由于消息分段需要被缓冲，直到可以转发一个完整的消息给下一个 ChannelInboundHandler，所以这个操作有轻微的开销。其所带来的好处便是你不必关心消息碎片了。
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;

    public HttpAggregatorInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (isClient) {
            pipeline.addLast("codec", new HttpClientCodec());
        } else {
            pipeline.addLast("codec", new HttpServerCodec());
        }
        // 将最大的消息大小为 512 KB的 HttpObjectAggregator 添加到 ChannelPipeline
        pipeline.addLast("aggregator",
                new HttpObjectAggregator(512 * 1024));
    }
}