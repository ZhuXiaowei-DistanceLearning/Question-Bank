package com.zxw.netty.handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zxw
 * @date 2024-05-24 11:08
 */
@Slf4j
public class ClientSendHandler extends ChannelOutboundHandlerAdapter {

    private static final int MAX_PACKET_SIZE = 3200;

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        if (msg instanceof ByteBuf) {
//            ByteBuf byteBuf = (ByteBuf) msg;
//            while (byteBuf.readableBytes() > MAX_PACKET_SIZE) {
//                ByteBuf chunk = byteBuf.readRetainedSlice(MAX_PACKET_SIZE);
//                ctx.write(chunk);
//            }
//            ctx.write(byteBuf, promise);
//        } else {
//            // For other message types, handle them as usual
//            super.write(ctx, msg, promise);
//        }
        super.write(ctx, msg, promise);
    }
}
