package com.zxw.kafka;

import com.alibaba.fastjson.JSONObject;
import com.zxw.base.ConsumerHandler;
import com.zxw.domain.ConsumerBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2021/8/12 15:32
 */
@Component
@Slf4j
public class KafkaConsumer implements ConsumerHandler<ConsumerRecord<?, ?>> {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 消息重复处理方案
     * 1.redis去重
     * @param record
     */
    @Override
    @KafkaListener(topics = {"app-log"})
    public void receiveMessage(ConsumerRecord<?, ?> record) {
        String body = (String) record.value();
        ConsumerBody parse = JSONObject.parseObject(body, ConsumerBody.class);
        log.info("接收到kafka消息;{}",parse);
    }

    @Override
    public String getHandlerName() {
        return "2";
    }
}
