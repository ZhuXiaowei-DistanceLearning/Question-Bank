package com.zxw.web;

import com.alibaba.fastjson.JSONObject;
import com.zxw.base.ProducerHandler;
import com.zxw.factory.ProducerFactory;
import com.zxw.web.consts.AiotRedisConstants;
import com.zxw.web.service.DelayJobService;
import com.zxw.vo.base.Result;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

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

//    @Autowired
    private DelayJobService delayJobService;

    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/sendMessage")
    public Result<String> sendMessage(String handlerName, String type) {
        EventLoopGroup loopGroup = new DefaultEventLoopGroup(2);
        ProducerHandler handler = producerFactory.getHandler(handlerName);
        send(handler);
        while (StringUtils.equals("loop", type)) {
            loopGroup.execute(() -> {
                send(handler);
            });
        }
        return Result.success("消息发送成功");
    }

    private void send(ProducerHandler handler) {
        redisTemplate.opsForSet().randomMember(AiotRedisConstants.DEVICE);
        JSONObject msg = new JSONObject();
        msg.put("time", System.currentTimeMillis());
        msg.put("prodId", UUID.randomUUID().toString());
        msg.put("guid", UUID.randomUUID().toString());
        msg.put("status", UUID.randomUUID().toString());
        msg.put("server", "192.168");
        handler.sendMessage(msg.toJSONString());
    }

    @PostMapping("/delayHandler")
    public Result<String> delayHandler(@RequestBody String message) {
        delayJobService.delayHandler(message);
        return Result.success("添加延时队列成功");
    }

}
