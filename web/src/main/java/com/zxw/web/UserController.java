package com.zxw.web;

import com.zxw.entity.User;
import com.zxw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("register")
    public void register() {
        //模拟10个用户注册
        IntStream.rangeClosed(1, 10).forEach(i -> {
            //落库
            User user = userService.register();
            //模拟50%的消息可能发送失败
            if (ThreadLocalRandom.current().nextInt(10) % 2 == 0) {
                //通过RabbitMQ发送消息
                rabbitTemplate.convertAndSend("", "RabbitConfiguration.ROUTIN");
                log.info("sent mq user {}", user.getId());
            }
        });
    }

    @PostMapping("")
    public Mono<User> save(User user) {
        return this.userService.save(user);
    }

    @DeleteMapping("/{username}")
    public Mono<Long> deleteByUsername(@PathVariable String username) {
        return this.userService.deleteByUsername(username);
    }

    @GetMapping("/{username}")
    public Mono<User> findByUsername(@PathVariable String username) {
        return this.userService.findByUsername(username);
    }

    @GetMapping("")
    public Flux<User> findAll() {
        return this.userService.findAll();
    }
}
