package com.zxw.kafka;

import com.zxw.base.ProducerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2021/8/12 15:26
 */
@Component
@Slf4j
public class KafkaProducer implements ProducerHandler {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(String message) {
        kafkaTemplate.send("test", message);
        log.info("消息发送成功");
    }

    @Override
    public String getHandlerName() {
        return "kafka";
    }
}
