package com.zxw.rabbit.delay;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息 TTL 过期（time to live，存活时间，可以用在限时支付消息）
 * 队列达到最大长度（队列满了，无法路由到该队列）
 * 消息被拒绝（ basic.reject / basic.nack ），并且 requeue = false
 * @author zxw
 * @date 2022/4/3 18:41
 */
@Configuration
public class DelayRabbitConfig {
    /**
     * 延时队列
     * 发送到该队列的message会在一段时间后过期进入到delay_process_queue
     * 队列里所有的message都有统一的失效时间
     */
    public static String DELAY_QUEUE = "zxw.test.normal.queue";

    /**
     * 延时的交换器
     */
    public static String DELAY_EXCHANGE = "zxw.test.normal.exchange";
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
     * x-message-tti(Time-To-Live)发送到队列的消息在丟弃之前可以存活多长时间(毫秒)
     * x-max-length限制队列最大长度（新增后挤出最早的），单位个数
     * x-expires队列没有访问超时时，自动删除（包含没有消费的消息），单位毫秒
     * x-max-length-bytes限制队列最大容量
     * x-dead-letter-exchange死信交换机，将删除/过期的数据，放入指定交换机
     * x-dead-letter-routing-key死信路由，将删除/过期的数据，放入指定routingKey
     * x-max-priority队列优先级x-queue-mode对列模式，默认lazy（将数据放入磁盘，消费时放入内存）
     * x-queue-master-locator镜像队列
     *
     * 作者：MrDong先生
     * 链接：https://juejin.cn/post/7108640680431812616
     * 来源：稀土掘金
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @return
     */
    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE)
                // DLX，dead letter发送到的exchange ,设置死信队列交换器到处理交换器
                .withArgument("x-dead-letter-exchange", PROCESS_EXCHANGE)
                // dead letter携带的routing key，配置处理队列的路由key
                // 队列设置最大长度
                .withArgument("x-dead-letter-routing-key", PROCESS_QUEUE)
                // 设置过期时间
                // 队列设置消息过期时间 60 秒
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
    public static String PROCESS_QUEUE = "zxw.test.delay.queue";

    /**
     * 处理的交换器
     */
    public static String PROCESS_EXCHANGE = "zxw.test.delay.exchange";

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
    DirectExchange processExchange() {
        return new DirectExchange(PROCESS_EXCHANGE);
    }

    /**
     * 将DLX绑定到实际消费队列
     *
     * @return
     */
    @Bean
    Binding processBinding(DirectExchange processExchange, Queue delayProcess) {
        return BindingBuilder
                .bind(delayProcess)
                .to(processExchange)
                .with(PROCESS_QUEUE);
    }


}
