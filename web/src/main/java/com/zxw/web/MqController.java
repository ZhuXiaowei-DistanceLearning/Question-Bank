package com.zxw.web;

import com.alibaba.fastjson.JSONObject;
import com.zxw.base.ProducerHandler;
import com.zxw.factory.ProducerFactory;
import com.zxw.service.DelayJobService;
import com.zxw.vo.base.Result;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author zxw
 * @date 2021/8/12 15:28
 */
@RestController
@RequestMapping("/mq")
public class MqController {

    @Autowired
    private ProducerFactory producerFactory;

    @Autowired
    private DelayJobService delayJobService;


    @GetMapping("/sendMessage")
    public Result<String> sendMessage(String handlerName) {
        EventLoopGroup loopGroup = new DefaultEventLoopGroup(2);
        ProducerHandler handler = producerFactory.getHandler(handlerName);
        while (true) {
            loopGroup.execute(() -> {
                JSONObject msg = new JSONObject();
                msg.put("timestamp", System.currentTimeMillis());
                msg.put("guid", UUID.randomUUID().toString());
                handler.sendMessage(msg.toJSONString());
            });
        }
//        return Result.success("消息发送成功");
    }

    @PostMapping("/delayHandler")
    public Result<String> delayHandler(@RequestBody String message) {
        delayJobService.delayHandler(message);
        return Result.success("添加延时队列成功");
    }

}
