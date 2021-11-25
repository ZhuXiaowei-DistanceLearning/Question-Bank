package com.zxw.designpattern.decorator;

/**
 * @author zxw
 * @date 2020-12-16 15:13
 */
public final class MongoHttpClients {
    public MongoHttpClients() {
    }

    public static MongoHttpClientFactory registrationClientFactory() {
        return canonicalClientFactory("");
    }

    static MongoHttpClientFactory canonicalClientFactory(final String name) {

        return new MongoHttpClientFactory() {
            @Override
            public MongoHttpClient newClient() {
                return new SessionMongoHttpClient(
                        name,
                        1000,
                        1000,
                        RetryableMongoHttpClient.createFactory(name,
                                RedirectingMongoHttpClient.createFactory())
                        );
            }

            @Override
            public void shutdown() {

            }
        };
    }
}
