package com.zxw.netflix;

import com.netflix.ribbon.hystrix.FallbackHandler;
import rx.Observable;

import java.util.Map;

/**
 * @author zxw
 * @date 2022/7/31 16:59
 */
public class RecommendationServiceFallbackHandler implements FallbackHandler {
    @Override
    public Observable getFallback(com.netflix.hystrix.HystrixInvokableInfo hystrixInfo, Map requestProperties) {
        return null;
    }
}
