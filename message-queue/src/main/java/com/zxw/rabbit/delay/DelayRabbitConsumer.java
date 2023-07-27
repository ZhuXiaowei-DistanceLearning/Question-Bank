package com.zxw.rabbit.delay;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2021-10-28 21:39
 */
//@Component
@Slf4j
public class DelayRabbitConsumer {

    @RabbitListener(queues = {"#{delayProcess.name}"})
    public void receiveDelay(Message message, Channel channel) {
        String body = String.valueOf(message.getBody());
        log.info("接收到延迟消息:{}", body);
    }

}
