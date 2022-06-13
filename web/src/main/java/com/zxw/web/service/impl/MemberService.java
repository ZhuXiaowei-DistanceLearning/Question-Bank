package com.zxw.web.service.impl;

import com.zxw.web.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MemberService {
    //发送欢迎消息的状态
    private Map<Long, Boolean> welcomeStatus = new ConcurrentHashMap<>();

    //监听用户注册成功的消息，发送欢迎消息
    @RabbitListener(queues = "")
    public void listen(User user) {
        log.info("receive mq user {}", user.getId());
        welcome(user);
    }

    //发送欢迎消息
    public void welcome(User user) {
        //去重操作
        if (welcomeStatus.putIfAbsent(user.getId(), true) == null) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }
            log.info("memberService: welcome new user {}", user.getId());
        }
    }
}