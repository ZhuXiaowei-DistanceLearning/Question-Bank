package com.zxw.rabbit;

import com.zxw.base.ConsumerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author zxw
 * @date 2021-10-28 21:39
 */
@Component
@Slf4j
public class DelayRabbitConsumer implements ConsumerHandler {
    @Override
    public String getHandlerName() {
        return null;
    }

    @Override
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "process.queue", durable = "true"),
            exchange = @Exchange(name = "process.queue.exchange",
                    durable = "true",
                    type = "topic",
                    ignoreDeclarationExceptions = "true"),
            key = "springboot.*"
    )
    )
    @RabbitHandler
    public void receiveMessage(Object o) {
        log.info("当前时间:{}", LocalDateTime.now());
        log.info("消费到数据:{}", o);
    }
}
