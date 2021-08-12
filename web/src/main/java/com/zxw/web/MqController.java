package com.zxw.web;

import com.zxw.base.ProducerHandler;
import com.zxw.factory.ProducerFactory;
import com.zxw.vo.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxw
 * @date 2021/8/12 15:28
 */
@RestController
@RequestMapping("/mq")
public class MqController {

    @Autowired
    private ProducerFactory producerFactory;

    @GetMapping("/sendMessage")
    public Result<String> sendMessage(String handlerName) {
        ProducerHandler handler = producerFactory.getHandler(handlerName);
        handler.sendMessage("");
        return Result.success("消息发送成功");
    }

}
