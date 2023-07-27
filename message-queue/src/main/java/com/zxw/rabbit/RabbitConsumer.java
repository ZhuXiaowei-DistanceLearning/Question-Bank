package com.zxw.rabbit;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zxw
 * @date 2021-10-28 21:39
 */
//@Component
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

    @RabbitListener(queues = {"zxw.test.custom.queue"})
    public void receiveCustom(Message message) {
        String body = String.valueOf(message.getBody());
        log.info("接收到custom消息:{}", body);
    }

    @RabbitListener(queues = {"#{topicQueue.name}"})
    public void receiveTopic(Message message) {
        String body = String.valueOf(message.getBody());
        log.info("接收到topic消息:{}", body);
    }

    @RabbitListener(queues = {"#{fanoutQueue.name}"})
    public void receiveFanout(Message message, Channel channel) throws IOException {
        String body = String.valueOf(message.getBody());
        log.info("接收到fanout消息:{}", body);
        try {
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            throw new RuntimeException();
        }  catch (Exception e) {

            if (message.getMessageProperties().getRedelivered()) {

                log.error("消息已重复处理失败,拒绝再次接收...");

                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // 拒绝消息
            } else {

                log.error("消息即将再次返回队列处理...");

                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

    @RabbitListener(queues = {"#{directQueue.name}"})
    public void receiveDirect(Message message) {
        String body = String.valueOf(message.getBody());
        log.info("接收到direct消息:{}", body);
    }
}
