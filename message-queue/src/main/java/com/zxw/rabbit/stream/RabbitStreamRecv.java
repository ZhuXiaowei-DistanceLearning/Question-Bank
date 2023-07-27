package com.zxw.rabbit.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2022/7/5 22:25
 */
//@Component
@EnableBinding(Sink.class)
@Slf4j
public class RabbitStreamRecv {

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {
        System.out.println("message = " + message.getPayload());
    }

}
