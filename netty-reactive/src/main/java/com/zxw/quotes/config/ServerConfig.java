package com.zxw.quotes.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

/**
 * @author zxw
 * @date 2024-05-22 17:12
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class ServerConfig {
    /**
     * 服务名
     */
    String name;
    /**
     * 端口
     */
    int port;
    /**
     * 订阅客户端名称
     */
    String subscribeName;
    /**
     * 是否开启
     */
    boolean enabled = true;
}
