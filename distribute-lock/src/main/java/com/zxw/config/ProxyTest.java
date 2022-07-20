package com.zxw.config;

/**
 * @author zxw
 * @date 2022-01-30 15:55
 */
public class ProxyTest {
    public static void main(String[] args) {
        SayService sayService = new SayServiceImpl();
        SayHelloInvoke invoke = new SayHelloInvoke(sayService);
        SayService proxy = (SayService) invoke.getProxy();
        proxy.say();
    }
}
