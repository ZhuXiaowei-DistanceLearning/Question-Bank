package com.zxw.rabbit.delay;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;

/**
 * @author zxw
 * @date 2022/4/3 18:41
 */
//@Configuration
public class DelayRabbitConfig {
    /**
     * 延时队列
     * 发送到该队列的message会在一段时间后过期进入到delay_process_queue
     * 队列里所有的message都有统一的失效时间
     */
    public static String DELAY_QUEUE = "delay.queue";

    /**
     * 延时的交换器
     */
    public static String DELAY_EXCHANGE = "delay.queue.exchange";
    /**
     * 超时时间
     */
    public static Long QUEUE_EXPIRATION = 4000L;

    /**
     * 配置延时交换器
     *
     * @return
     */
    @Bean
    DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE);
    }

    /**
     * 配置延时队列
     *
     * @return
     */
    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE)
                // DLX，dead letter发送到的exchange ,设置死信队列交换器到处理交换器
                .withArgument("x-dead-letter-exchange", PROCESS_EXCHANGE)
                // dead letter携带的routing key，配置处理队列的路由key
                .withArgument("x-dead-letter-routing-key", PROCESS_QUEUE)
                // 设置过期时间
                .withArgument("x-message-ttl", QUEUE_EXPIRATION)
                .build();
    }

    /**
     * 将delayQueue2绑定延时交换机中，routingKey为队列名称
     *
     * @return
     */
    @Bean
    Binding queueTTLBinding(DirectExchange delayExchange, Queue delayQueue) {
        return BindingBuilder
                .bind(delayQueue)
                .to(delayExchange)
                .with(DELAY_QUEUE);
    }

    /**
     * 实际消费队列
     * message失效后进入的队列，也就是实际的消费队列
     */
    public static String PROCESS_QUEUE = "process.queue";

    /**
     * 处理的交换器
     */
    public static String PROCESS_EXCHANGE = "process.queue.exchange";

    /**
     * 设置处理队列
     *
     * @return
     */
    @Bean
    public Queue delayProcess() {
        return QueueBuilder
                .durable(PROCESS_QUEUE)
                .build();
    }

    /**
     * 配置处理交换器
     *
     * @return
     */
    @Bean
    TopicExchange processExchange() {
        return new TopicExchange(PROCESS_EXCHANGE);
    }

    /**
     * 将DLX绑定到实际消费队列
     *
     * @return
     */
    @Bean
    Binding processBinding(TopicExchange topicExchange, Queue delayProcess) {
        return BindingBuilder
                .bind(delayProcess)
                .to(topicExchange)
                .with(PROCESS_QUEUE);
    }


}
