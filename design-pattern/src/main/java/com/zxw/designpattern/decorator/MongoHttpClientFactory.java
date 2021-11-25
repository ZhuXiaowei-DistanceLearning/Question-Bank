package com.zxw.designpattern.decorator;

/**
 * @author zxw
 * @date 2020-12-16 15:02
 */
public interface MongoHttpClientFactory {
    MongoHttpClient newClient();

    void shutdown();
}
