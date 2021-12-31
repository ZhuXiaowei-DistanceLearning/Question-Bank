package com.zxw.netty.demo.encode;

import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectDecoder;

/**
 * @author zxw
 * @date 2021/12/31 15:58
 */
public class HttpDefineDecoder extends HttpObjectDecoder {
    @Override
    protected boolean isDecodingRequest() {
        return false;
    }

    @Override
    protected HttpMessage createMessage(String[] strings) throws Exception {
        return null;
    }

    @Override
    protected HttpMessage createInvalidMessage() {
        return null;
    }
}
