package com.zxw.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;
@Configuration
public class LettuceClientConfigurationFactory implements FactoryBean<LettuceClientConfiguration> {

    @Override
    public LettuceClientConfiguration getObject() throws Exception {
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(200);
        poolConfig.setMaxWaitMillis(2000);
        poolConfig.setTestOnBorrow(true);
        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig).commandTimeout(Duration.ofMillis(3000)).shutdownTimeout(Duration.ZERO).build();

        return lettuceClientConfiguration;
    }

    @Override
    public Class<?> getObjectType() {
        return LettuceClientConfiguration.class;
    }

}