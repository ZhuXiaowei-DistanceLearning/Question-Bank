package com.zxw.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @author zxw
 * @date 2022/7/9 17:22
 */
@Configuration
@Slf4j
public class RabbitMqListener implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    private RabbitMqListener(){

    }

    public RabbitMqListener(RabbitTemplate rabbitTemplate){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("发送者发送成功:{}", ack);
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("消费者是否消费成功:{}", returned);
    }
}
