package designpattern.decorator;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zxw
 * @date 2020-12-16 14:45
 */
public class SessionMongoHttpClient extends MongoHttpClientDecorator {
    private final String name;
    private final long sessionDurationMs;
    private final long currentSessionDurationMs;
    private volatile long lastReconnectTimeStamp = -1;
    private final AtomicReference<MongoHttpClient> eurekaHttpClientRef = new AtomicReference<>();
    private final MongoHttpClientFactory clientFactory;

    public SessionMongoHttpClient(String name, long sessionDurationMs, long currentSessionDurationMs, MongoHttpClientFactory clientFactory) {
        this.name = name;
        this.sessionDurationMs = sessionDurationMs;
        this.currentSessionDurationMs = currentSessionDurationMs;
        this.clientFactory = clientFactory;
    }

    @Override
    protected <R> List<R> execute(RequestExecutor<R> requestExecutor) {
        long now = System.currentTimeMillis();
        long delay = now - lastReconnectTimeStamp;
        if (delay >= currentSessionDurationMs) {
            lastReconnectTimeStamp = now;
        }
        MongoHttpClient eurekaHttpClient = eurekaHttpClientRef.get();
        if (eurekaHttpClient == null) {
            eurekaHttpClient = TransportUtils.getOrSetAnotherClient(eurekaHttpClientRef, clientFactory.newClient());
        }
        return requestExecutor.execute(eurekaHttpClient);
    }

    @Override
    public void shutdown() {

    }
}
