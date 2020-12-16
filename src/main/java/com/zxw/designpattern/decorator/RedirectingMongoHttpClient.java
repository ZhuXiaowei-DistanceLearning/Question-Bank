package com.zxw.designpattern.decorator;

import java.util.List;

/**
 * @author zxw
 * @date 2020-12-16 14:45
 */
public class RedirectingMongoHttpClient extends MongoHttpClientDecorator{
    @Override
    protected <R> List<R> execute(RequestExecutor<R> requestExecutor) {
        return null;
    }

    public static MongoHttpClientFactory createFactory() {
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

    @Override
    public void shutdown() {

    }
}
