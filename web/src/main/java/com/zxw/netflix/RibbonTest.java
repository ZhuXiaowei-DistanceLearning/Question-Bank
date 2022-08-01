package com.zxw.netflix;


import com.netflix.ribbon.ClientOptions;
import com.netflix.ribbon.Ribbon;
import com.netflix.ribbon.RibbonRequest;
import com.netflix.ribbon.http.HttpRequestTemplate;
import com.netflix.ribbon.http.HttpResourceGroup;
import com.netflix.ribbon.proxy.annotation.Http;
import com.netflix.ribbon.proxy.annotation.Var;
import io.netty.buffer.ByteBuf;
import rx.Observable;

/**
 * @author zxw
 * @date 2022/7/31 16:19
 */
public class RibbonTest {
    public static void main(String[] args) {
        MovieService movieService = Ribbon.from(MovieService.class);
        Observable<ByteBuf> result = movieService.recommendationsByUserId("user1").observe();
    }

    public void test() {
        HttpResourceGroup httpResourceGroup = Ribbon.createHttpResourceGroup("movieServiceClient",
                ClientOptions.create()
                        .withMaxAutoRetriesNextServer(3)
                        .withConfigurationBasedServerList("localhost:8080,localhost:8088"));
        HttpRequestTemplate<ByteBuf> recommendationsByUserIdTemplate = httpResourceGroup.newTemplateBuilder("recommendationsByUserId", ByteBuf.class)
                .withMethod("GET")
                .withUriTemplate("/users/{userId}/recommendations")
                .withFallbackProvider(new RecommendationServiceFallbackHandler())
//                .withResponseValidator(new RecommendationServiceResponseValidator())
                .build();
        Observable<ByteBuf> result = recommendationsByUserIdTemplate.requestBuilder()
                .withRequestProperty("userId", "user1")
                .build()
                .observe();
    }

    public interface MovieService {
        @Http(
                method = Http.HttpMethod.GET,
                uri = "/users/{userId}/recommendations"
        )
        RibbonRequest<ByteBuf> recommendationsByUserId(@Var("userId") String userId);
    }

}
