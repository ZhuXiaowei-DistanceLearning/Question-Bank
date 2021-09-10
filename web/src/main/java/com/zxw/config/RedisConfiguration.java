package com.zxw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

/**
 * @author zxw
 * @date 2021/8/31 17:13
 */
//@Configuration
public class RedisConfiguration {

    @Bean
    public ReactiveStringRedisTemplate redisTemplate(LettuceConnectionFactory factory) {
        ReactiveStringRedisTemplate redisTemplate = new ReactiveStringRedisTemplate(factory);
        return redisTemplate;
    }
}
