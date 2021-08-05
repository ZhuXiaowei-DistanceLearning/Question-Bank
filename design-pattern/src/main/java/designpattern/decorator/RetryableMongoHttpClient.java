package designpattern.decorator;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zxw
 * @date 2020-12-16 14:45
 */
public class RetryableMongoHttpClient extends MongoHttpClientDecorator {
    private final String name;
    private final MongoHttpClientFactory clientFactory;
    private static final int numberOfRetries = 3;
    private final AtomicReference<MongoHttpClient> delegate = new AtomicReference<>();

    public RetryableMongoHttpClient(String name, MongoHttpClientFactory clientFactory) {
        this.name = name;
        this.clientFactory = clientFactory;
    }

    @Override
    protected <R> List<R> execute(RequestExecutor<R> requestExecutor) {
        for (int i = 0; i < numberOfRetries; i++) {
            MongoHttpClient mongoHttpClient = delegate.get();
            if (mongoHttpClient == null) {
                mongoHttpClient = clientFactory.newClient();
            }
            try {
                List<R> execute = requestExecutor.execute(mongoHttpClient);
                return execute;
            } catch (Exception e) {

            }
            delegate.compareAndSet(mongoHttpClient, null);
        }
        throw new RuntimeException("Retry limit reached; giving up on completing the request");
    }

    public static MongoHttpClientFactory createFactory(String name, MongoHttpClientFactory mongoHttpClientFactory) {
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
