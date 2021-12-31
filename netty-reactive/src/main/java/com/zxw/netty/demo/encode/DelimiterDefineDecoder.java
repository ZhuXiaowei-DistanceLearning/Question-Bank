package com.zxw.netty.demo.encode;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * @author zxw
 * @date 2021/12/31 15:58
 */
public class DelimiterDefineDecoder extends DelimiterBasedFrameDecoder {
    public DelimiterDefineDecoder(int maxFrameLength, ByteBuf delimiter) {
        super(maxFrameLength, delimiter);
    }
}
