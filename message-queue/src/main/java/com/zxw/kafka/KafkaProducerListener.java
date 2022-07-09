package com.zxw.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2022/7/9 12:11
 */
@Component
@Slf4j
public class KafkaProducerListener implements ProducerListener<Object, Object> {
    @Override
    public void onSuccess(ProducerRecord<Object, Object> producerRecord, RecordMetadata recordMetadata) {
        log.info("Message send success : " + producerRecord.toString());
    }

    @Override
    public void onError(ProducerRecord<Object, Object> producerRecord, RecordMetadata recordMetadata, Exception exception) {
        log.info("Message send error : " + producerRecord.toString());
    }
}
