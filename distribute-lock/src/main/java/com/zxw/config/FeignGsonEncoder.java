package com.zxw.config;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.gson.GsonEncoder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

/**
 * @author zxw
 * @date 2022-01-30 18:21
 */
@Slf4j
public class FeignGsonEncoder implements Encoder {
    final Encoder encoder = new GsonEncoder();
    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        log.info("执行编码操作");
        encoder.encode(object,bodyType,template);
    }
}
