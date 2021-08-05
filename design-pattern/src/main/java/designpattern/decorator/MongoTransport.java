package designpattern.decorator;

/**
 * @author zxw
 * @date 2020-12-16 15:53
 */
public class MongoTransport {
    private MongoHttpClient queryClient;
    private MongoHttpClientFactory queryClientFactory;
    void shutdown() {

        if (queryClient != null) {
            queryClient.shutdown();
        }

        if (queryClientFactory != null) {
            queryClientFactory.shutdown();
        }
    }
}
