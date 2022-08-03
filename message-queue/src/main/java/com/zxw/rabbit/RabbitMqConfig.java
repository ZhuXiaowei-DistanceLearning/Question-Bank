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

import java.util.HashMap;
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
     * 处理路由键。需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。这是一个完整的匹配。如果一个队列绑定到该交换机上要求路由键 “abc”，则只有被标记为“abc”的消息才被转发，不会转发abc.def，也不会转发dog.ghi，只会转发abc。
     *
     * @return
     */
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("zxw.test.direct");
    }


    /**
     * 将路由键和某模式进行匹配。此时队列需要绑定要一个模式上。符号“#”匹配一个或多个词，符号“”匹配不多不少一个词。因此“abc.#”能够匹配到“abc.def.ghi”，但是“abc.” 只会匹配到“abc.def”。
     * @return
     */
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("zxw.test.topic");
    }

    /**
     * 不处理路由键。你只需要简单的将队列绑定到交换机上。一个发送到交换机的消息都会被转发到与该交换机绑定的所有队列上。很像子网广播，每台子网内的主机都获得了一份复制的消息。Fanout交换机转发消息是最快的。
     *
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("zxw.test.fanout");
    }

    @Bean
    public CustomExchange customExchange() {
        Map<String, Object> args = new HashMap<>(1);
        // 自定义交换机的类型
        args.put("x-delayed-type", "direct");
        return new CustomExchange("zxw.test.custom.exchange", "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding customBinding(Queue customQueue, CustomExchange customExchange) {
        return BindingBuilder.bind(customQueue).to(customExchange).with("delay").noargs();
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
    Queue customQueue() {
        return new Queue("zxw.test.custom.queue", true, false, false);
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
