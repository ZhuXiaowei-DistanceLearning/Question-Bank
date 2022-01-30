package com.zxw.config;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author zxw
 * @date 2022-01-30 21:50
 */
@Slf4j
public class FeignGsonDecoder implements Decoder {

    final Decoder decoder = new GsonDecoder();

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        log.info("执行解码操作");
        return decoder.decode(response,type);
    }
}
