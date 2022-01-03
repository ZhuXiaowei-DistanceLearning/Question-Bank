package com.zxw.netty.rpc;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String message) {
        System.out.println("收到客户端消息=" + message);
        //根据 message 返回不同的结果
        if(message != null) {
            return "你好客户端，我已经收到你的消息【" + message + "】";
        } else {
            return "你好客户端，我已经收到你的消息。";
        }
    }
}
