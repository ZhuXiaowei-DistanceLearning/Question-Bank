package com.zxw.rabbit;

import com.zxw.base.ProducerHandler;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2021-10-28 21:39
 */
@Component
public class RabbitProducer implements ProducerHandler {
    @Override
    public String getHandlerName() {
        return null;
    }

    @Override
    public void sendMessage(String message) {

    }
}
