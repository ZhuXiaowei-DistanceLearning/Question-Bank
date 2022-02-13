package com.zxw.rocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author zxw
 * @date 2022/2/12 22:40
 */
@Component
@RocketMQMessageListener(topic = "test", consumerGroup = "my-producer-group")
@Slf4j
public class RocketConsumer implements RocketMQListener<String> {

    public void receiveMessage(Object o) {
        DefaultLitePullConsumer litePullConsumer = new DefaultLitePullConsumer("lite");
        litePullConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            Collection<MessageQueue> messageQueues = litePullConsumer.fetchMessageQueues("");
            litePullConsumer.subscribe("", "");
            litePullConsumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(String message) {
        log.info("rocketmq接收到消息" + message);
    }
}
