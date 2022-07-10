package com.zxw.rabbit;

import cn.hutool.core.util.IdUtil;
import com.zxw.base.ProducerHandler;
import com.zxw.constants.QueueConsts;
import com.zxw.rabbit.delay.DelayRabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public String getHandlerName() {
        return "rabbit";
    }

    @Override
    public void sendMessage(String message) {
        CorrelationData correlationData = new CorrelationData(IdUtil.fastSimpleUUID());
        rabbitTemplate.convertAndSend(QueueConsts.EXCHANGE_DIRECT_NAME, "direct-1", message, msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(10000));
            return msg;
        });
        rabbitTemplate.convertAndSend(QueueConsts.EXCHANGE_TOPIC_NAME, "topic-1", message, msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(10000));
            return msg;
        });
        rabbitTemplate.convertAndSend(QueueConsts.EXCHANGE_FANOUT_NAME, "test", message, msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(10000));
            return msg;
        });
        rabbitTemplate.convertAndSend("test1231231231", message, msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(10000));
            return msg;
        });
        rabbitTemplate.convertAndSend("zxw.test.custom.exchange", "delay", message, msg -> {
            msg.getMessageProperties().setDelay(10000);
            return msg;
        });
        amqpTemplate.convertAndSend(DelayRabbitConfig.DELAY_EXCHANGE, DelayRabbitConfig.DELAY_QUEUE, message, msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(10000));
            return msg;
        });
//        amqpTemplate.convertAndSend("delay.queue", message, msg -> {
//            msg.getMessageProperties().setExpiration(String.valueOf(2000));
//            return msg;
//        });
    }
}
