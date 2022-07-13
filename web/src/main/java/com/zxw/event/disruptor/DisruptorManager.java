package com.zxw.event.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zxw
 * @date 2022/7/12 14:43
 */
@Component
public class DisruptorManager {

    private static final int RING_BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        DisruptorManager disruptorManager = new DisruptorManager();
        RingBuffer<DisruptorModel> ringBuffer = disruptorManager.messageModelRingBuffer();
        ringBuffer.publishEvent((event, sequence, data) -> event.setBody(data), "123456"); //lambda式写法，如果是用jdk1.8以下版本使用以上注释的一段

    }

    @Bean
    public RingBuffer<DisruptorModel> messageModelRingBuffer() {
        // 定义用于事件处理的线程池，
        // Disruptor通过java.util.concurrent.ExecutorSerivce提供的线程来触发consumer的事件处理
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 指定事件工厂
        HelloEventFactory factory = new HelloEventFactory();

        // 指定ringbuffer字节大小，必须为2的N次方（能将求模运算转为位运算提高效率），否则将影响效率
        int bufferSize = 1024 * 256;

        // 单线程模式，获取额外的性能
        Disruptor<DisruptorModel> disruptor = new Disruptor<>(factory, RING_BUFFER_SIZE, Executors.defaultThreadFactory(), ProducerType.SINGLE, new BlockingWaitStrategy());

        // 设置事件业务处理器---消费者
        disruptor.handleEventsWith(new HelloEventHandler());

        // 启动disruptor线程
        disruptor.start();

        // 获取ringbuffer环，用于接取生产者生产的事件
        RingBuffer<DisruptorModel> ringBuffer = disruptor.getRingBuffer();

        return ringBuffer;
    }
}
