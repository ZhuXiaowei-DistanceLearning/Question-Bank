package com.zxw.rabbit.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author zxw
 * @date 2022/7/5 22:53
 */
//@Component
@Slf4j
public class RabbitStreamConsumer {
    @Bean
    public Consumer<String> demoChannel() {
        return message -> {
            log.info("接收消息为：{}", message);
        };
    }
}
