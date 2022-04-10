package com.zxw.rabbit;

import com.zxw.base.ProducerHandler;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2021-10-28 21:39
 */
@Component
public class RabbitProducer implements ProducerHandler {

    @Autowired
    private AmqpTemplate amqpTemplate;
 
    @Override
    public String getHandlerName() {
        return "rabbit";
    }

    @Override
    public void sendMessage(String message) {
        amqpTemplate.convertAndSend("delay.queue", message, msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(10000));
            return msg; 
        });

        amqpTemplate.convertAndSend("delay.queue", message, msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(2000));
            return msg;
        });
    }
}
