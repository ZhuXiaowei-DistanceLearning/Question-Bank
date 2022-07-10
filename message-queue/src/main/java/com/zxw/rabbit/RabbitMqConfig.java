package com.zxw.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author zxw
 * @date 2022/7/9 15:00
 */
@Configuration
@Slf4j
public class RabbitMqConfig {

    @Bean(name = "rabbitListenerContainerFactory")
    @ConditionalOnProperty(prefix = "spring.rabbitmq.listener", name = "type", havingValue = "simple")
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setAfterReceivePostProcessors(new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                Map header = message.getMessageProperties().getHeaders();
                //判断是否压测消息，是的话要动态切换影子库跟后续操作
                if (header.containsKey("test")) {
                    log.info("带压测标记！");
                }
                return message;
            }
        });
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    /**
     * 路由模式
     * 路由模式是可以根据路由键选择性给多个消费者发送消息的模式。它包含一个
     * 发布者、两个消费者、两个队列和一个交换机。两个消费者同时绑定到不同的
     * 队列上去，两个队列通过路由键绑定到交换机上去，发布者发送消息到交换
     * 机，交换机通过路由键转发到不同队列，队列绑定的消费者接收并消费消息。
     *
     * @return
     */
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("zxw.test.direct");
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("zxw.test.topic");
    }

    /**
     * 广播模式，发送给所有队列
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("zxw.test.fanout");
    }

    /**
     * durable: 是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
     * exclusive: 默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
     * autoDelete: 是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
     *
     * @return
     */
    @Bean
    Queue topicQueue() {
        return new Queue("zxw.test.topic.queue", true, false, false);
    }

    @Bean
    Queue directQueue() {
        return new Queue("zxw.test.direct.queue", true, false, false);
    }

    @Bean
    Queue fanoutQueue() {
        return new Queue("zxw.test.fanout.queue", true, false, false);
    }

    @Bean
    Binding directBinding(DirectExchange directExchange, Queue directQueue) {
        return BindingBuilder.bind(directQueue).to(directExchange).with("direct-1");
    }

    @Bean
    Binding topicBinding(TopicExchange topicExchange, Queue topicQueue) {
        return BindingBuilder.bind(topicQueue).to(topicExchange).with("topic-1");
    }

    @Bean
    Binding fanoutBinding(FanoutExchange fanoutExchange, Queue fanoutQueue) {
        return BindingBuilder.bind(fanoutQueue).to(fanoutExchange);
    }

}
