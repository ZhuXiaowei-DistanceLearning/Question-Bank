package com.zxw.config;

import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zxw
 * @date 2022-01-24 21:15
 */
@Configuration
public class FeignConfig {
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("", "");
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(5000, 1000);
    }
}
