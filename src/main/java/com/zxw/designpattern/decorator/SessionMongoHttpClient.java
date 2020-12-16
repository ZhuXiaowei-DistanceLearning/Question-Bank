package com.zxw.designpattern.decorator;

import java.util.List;

/**
 * @author zxw
 * @date 2020-12-16 14:45
 */
public class SessionMongoHttpClient extends MongoHttpClientDecorator {
    private final String name;
    private final MongoHttpClientFactory clientFactory;

    public SessionMongoHttpClient(String name, MongoHttpClientFactory clientFactory) {
        this.name = name;
        this.clientFactory = clientFactory;
    }

    @Override
    protected <R> List<R> execute(RequestExecutor<R> requestExecutor) {
        return null;
    }
}
