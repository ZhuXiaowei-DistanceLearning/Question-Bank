package com.zxw.repository;

import com.zxw.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * @author zxw
 * @date 2021/10/29 23:13
 */
public interface UserRepository extends ReactiveCrudRepository<User, String> {
    Mono<User> findByUsername(String username);

    Mono<Long> deleteByUsername(String username);
}
