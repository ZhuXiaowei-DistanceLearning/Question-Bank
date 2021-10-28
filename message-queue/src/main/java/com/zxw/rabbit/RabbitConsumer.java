package com.zxw.rabbit;

import com.zxw.base.ConsumerHandler;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2021-10-28 21:39
 */
@Component
public class RabbitConsumer implements ConsumerHandler {
    @Override
    public String getHandlerName() {
        return null;
    }

    @Override
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-1", durable = "true"),
            exchange = @Exchange(name = "exchange-1",
                    durable = "true",
                    type = "topic",
                    ignoreDeclarationExceptions = "true"),
            key = "springboot.*"
    )
    )
    @RabbitHandler
    public void receiveMessage(Object o) {

    }
}
