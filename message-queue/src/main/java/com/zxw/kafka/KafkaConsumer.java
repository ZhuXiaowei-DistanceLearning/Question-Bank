package com.zxw.kafka;

import com.zxw.base.ConsumerHandler;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2021/8/12 15:32
 */
@Component
public class KafkaConsumer implements ConsumerHandler<ConsumerRecord<?, ?>> {
    @Override
    @KafkaListener(topics = {"test"})
    public void receiveMessage(ConsumerRecord<?, ?> consumerRecord) {

    }

    @Override
    public String getHandlerName() {
        return "2";
    }
}
