package com.zxw.rabbit.stream;

import com.zxw.base.ProducerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;

/**
 * @author zxw
 * @date 2022/7/5 22:18
 */
//@EnableBinding(Source.class)
@Slf4j
public class RabbitStream implements ProducerHandler {

    @Resource
    private MessageChannel output; // 消息发送管道

    @Autowired
    private StreamBridge streamBridge;


    public void send(){

    }

    @Override
    public String getHandlerName() {
        return "rabbit-stream";
    }

    @Override
    public void sendMessage(String message) {
        output.send(MessageBuilder.withPayload(message).build());
        streamBridge.send("demoChannel-out-0",MessageBuilder.withPayload(message).build());
    }
}
