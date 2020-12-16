package com.zxw.designpattern.decorator;

import java.util.List;

/**
 * @author zxw
 * @date 2020-12-16 14:45
 */
public class RetryableMongoHttpClient extends MongoHttpClientDecorator {
    private final String name;
    private final MongoHttpClientFactory clientFactory;

    public RetryableMongoHttpClient(String name, MongoHttpClientFactory clientFactory) {
        this.name = name;
        this.clientFactory = clientFactory;
    }

    @Override
    protected <R> List<R> execute(RequestExecutor<R> requestExecutor) {
        return null;
    }

    public static MongoHttpClientFactory createFactory(String name,MongoHttpClientFactory mongoHttpClientFactory) {
        return new MongoHttpClientFactory() {
            @Override
            public MongoHttpClient newClient() {
                return null;
            }

            @Override
            public void shutdown() {

            }
        };
    }
}
