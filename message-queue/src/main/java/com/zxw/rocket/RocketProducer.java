package com.zxw.rocket;

import com.zxw.base.ProducerHandler;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @author zxw
 * @date 2022/2/12 22:40
 */
@Component
public class RocketProducer implements ProducerHandler {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public String getHandlerName() {
        return "rocket";
    }

    @Override
    public void sendMessage(String message) {
        rocketMQTemplate.convertAndSend("test", message);
    }

    private void send() {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("localhost:9876");
        try {
            producer.start();
            for (int i = 0; i < 100; i++) { //Create a message instance, specifying topic, tag and message body.
                Message msg = new Message("TopicTest" /* Topic */, "TagA" /* Tag */, ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */);
                producer.sendOneway(msg);
                Thread.sleep(5000);
                producer.shutdown();
            }
        } catch (MQClientException | RemotingException | InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
