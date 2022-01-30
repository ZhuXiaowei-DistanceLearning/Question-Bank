package com.zxw.config;

import java.lang.reflect.Proxy;

/**
 * @author zxw
 * @date 2022-01-30 15:55
 */
public class ProxyTest {
    public static void main(String[] args) {
        SayService sayService = new SayServiceImpl();
        SayService proxy = (SayService) Proxy.newProxyInstance(SayService.class.getClassLoader(),
                new Class[]{SayService.class}, new SayHelloInvoke(sayService));
        proxy.say();
    }
}
