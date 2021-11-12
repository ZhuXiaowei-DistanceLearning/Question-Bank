package com.zxw.socket;

/**
 * @author zxw
 * @date 2021/11/12 15:46
 */
public class StaticResourceProcessor {
    public void process(Request request, Response response) {
        response.sendStaticResource();
    }
}
