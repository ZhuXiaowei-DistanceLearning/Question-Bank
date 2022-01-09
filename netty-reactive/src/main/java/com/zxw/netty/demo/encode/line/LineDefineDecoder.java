package com.zxw.netty.demo.encode.line;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @author zxw
 * @date 2021/12/31 15:56
 */
public class LineDefineDecoder extends LineBasedFrameDecoder {

    public LineDefineDecoder(int maxLength) {
        super(maxLength);
    }

    public LineDefineDecoder(int maxLength, boolean stripDelimiter, boolean failFast) {
        super(maxLength, stripDelimiter, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, buffer);
        if (frame == null) {
            return null;
        }
        Cmd cmd = new Cmd();
        cmd.setName(frame);
        return cmd;
    }


}
