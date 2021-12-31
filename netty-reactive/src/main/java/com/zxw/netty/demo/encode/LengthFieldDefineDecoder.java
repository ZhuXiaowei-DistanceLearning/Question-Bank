package com.zxw.netty.demo.encode;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author zxw
 * @date 2021/12/31 15:58
 */
public class LengthFieldDefineDecoder extends LengthFieldBasedFrameDecoder {
    public LengthFieldDefineDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }
}
