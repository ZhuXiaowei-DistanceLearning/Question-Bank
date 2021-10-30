package com.zxw.service;

import com.zxw.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author zxw
 * @date 2021/10/29 23:14
 */
public interface UserService {
    Mono<Long> deleteByUsername(String username);

    Mono<User> save(User user);

    Mono<User> findByUsername(String username);

    Flux<User> findAll();
}
