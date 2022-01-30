package com.zxw.config;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zxw
 * @date 2022-01-30 15:56
 */
@Slf4j
public class SayHelloInvoke implements InvocationHandler {
    private final Object obj;

    public SayHelloInvoke(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("代理方法执行：{}", proxy);
        return method.invoke(obj, args);
    }
}
