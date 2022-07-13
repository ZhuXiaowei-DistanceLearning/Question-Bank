package com.zxw.event.disruptor;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloEventHandler implements EventHandler<DisruptorModel> {


    public HelloEventHandler() {
    }

    @Override
    public void onEvent(DisruptorModel event, long sequence, boolean endOfBatch) {
        try {
            log.info("获取到disruptorModel事件:{},sequence:{},endOfBatch:{}", event.getBody(), sequence, event);
        } catch (Exception e) {
            log.info("消费者处理消息失败");
        }
        log.info("消费者处理消息结束");
    }
}