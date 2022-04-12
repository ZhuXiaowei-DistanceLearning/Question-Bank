package com.zxw.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author zxw
 * @date 2022/4/12 10:58
 */
@Configuration
@EnableConfigurationProperties
@EnableAspectJAutoProxy(exposeProxy = false)
public class CommonConfig {
}
