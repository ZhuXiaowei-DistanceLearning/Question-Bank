package com.zxw.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2021-10-28 21:39
 */
@Component
@Slf4j
public class RabbitConsumer {

//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "queue-1", durable = "true"),
//            exchange = @Exchange(name = "exchange-1",
//                    durable = "true",
//                    type = "topic",
//                    ignoreDeclarationExceptions = "true"),
//            key = "springboot.*"
//    )
//    )
//    @RabbitHandler
//    public void receiveMessage(Message message) {
//
//    }

    @RabbitListener(queues = {"#{topicQueue.name}"})
    public void receiveTopic(Message message) {
        String body = String.valueOf(message.getBody());
        log.info("接收到topic消息:{}", body);
    }

    @RabbitListener(queues = {"#{fanoutQueue.name}"})
    public void receiveFanout(Message message) {
        String body = String.valueOf(message.getBody());
        log.info("接收到fanout消息:{}", body);
    }

    @RabbitListener(queues = {"#{directQueue.name}"})
    public void receiveDirect(Message message) {
        String body = String.valueOf(message.getBody());
        log.info("接收到direct消息:{}", body);
    }
}
