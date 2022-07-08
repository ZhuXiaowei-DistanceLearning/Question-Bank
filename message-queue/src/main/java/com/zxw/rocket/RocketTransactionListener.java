package com.zxw.rocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author zxw
 * @date 2022/7/8 23:55
 */
@Component
@RocketMQTransactionListener
public class RocketTransactionListener implements RocketMQLocalTransactionListener {

    int a = 0;

    /**
     * 执行本地事务；
     *
     * @param msg Half(prepare) message
     * @param arg Custom business parameter
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String body = new String((byte[]) msg.getPayload(), StandardCharsets.UTF_8);
        JSONObject jsonObject = JSON.parseObject(body);
        a++;
        switch (a % 3) {
            case 0:
                return RocketMQLocalTransactionState.UNKNOWN;
            case 1:
                return RocketMQLocalTransactionState.COMMIT;
            case 2:
                return RocketMQLocalTransactionState.ROLLBACK;
            default:
                return RocketMQLocalTransactionState.UNKNOWN;
        }

    }

    /**
     * 回查本地事务状态，根据这次回查的结果来决定此次事务是提交还是回滚；
     *
     * @param msg Check message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        return RocketMQLocalTransactionState.COMMIT;
    }

}
