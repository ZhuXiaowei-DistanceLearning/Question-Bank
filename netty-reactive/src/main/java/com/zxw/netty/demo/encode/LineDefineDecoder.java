package com.zxw.netty.demo.encode;

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
}
