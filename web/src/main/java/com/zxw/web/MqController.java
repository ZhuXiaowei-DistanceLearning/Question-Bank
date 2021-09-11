package com.zxw.web;

import com.alibaba.fastjson.JSONObject;
import com.zxw.base.ProducerHandler;
import com.zxw.factory.ProducerFactory;
import com.zxw.vo.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zxw
 * @date 2021/8/12 15:28
 */
@RestController
@RequestMapping("/mq")
public class MqController {

    @Autowired
    private ProducerFactory producerFactory;

    ExecutorService executorService = Executors.newFixedThreadPool(16);

    @GetMapping("/sendMessage")
    public Result<String> sendMessage(String handlerName) {
        ProducerHandler handler = producerFactory.getHandler(handlerName);
        while (true) {
            executorService.execute(() -> {
                JSONObject msg = new JSONObject();
                msg.put("timestamp", System.currentTimeMillis());
                msg.put("guid", UUID.randomUUID().toString());
                handler.sendMessage(msg.toJSONString());
            });
        }
//        return Result.success("消息发送成功");
    }

}
