package com.zxw.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zxw
 * @date 2021/8/31 17:13
 */
@Configuration
public class RedisConfiguration {

    @Bean
    public ReactiveStringRedisTemplate reactiveStringRedisTemplate(LettuceConnectionFactory factory) {
        ReactiveStringRedisTemplate redisTemplate = new ReactiveStringRedisTemplate(factory);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        // key采用String序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // value采用fast-json序列化方式。
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties){
        Config config = new Config();
        List<String> cluster = redisProperties.getCluster().getNodes()
                .stream().map(node -> "redis://" + node).collect(Collectors.toList());
        config
                .setLockWatchdogTimeout(10000)
                .useClusterServers()
//                .setPassword("foobared")
                .setNodeAddresses(cluster);
//        String redisUrl = String.format("redis://%s:%s",redisProperties.getHost()+"",redisProperties.getPort()+"");
//        config.useSingleServer().setAddress(redisUrl).setPassword(redisProperties.getPassword());
        return Redisson.create(config);
    }
}
