package com.zxw.service.impl;

import com.zxw.entity.User;
import com.zxw.repository.UserRepository;
import com.zxw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author zxw
 * @date 2021/10/29 23:14
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * 保存或更新。
     * 如果传入的user没有id属性，由于username是unique的，在重复的情况下有可能报错，
     * 这时找到以保存的user记录用传入的user更新它。
     */
    @Override
    public Mono<User> save(User user) {
        return userRepository.save(user)
                // onErrorResume进行错误处理；
                .onErrorResume(e ->     // 1
                        userRepository.findByUsername(user.getUsername())   // 2
                                .flatMap(originalUser -> {      // 4
                                    user.setId(originalUser.getId());
                                    return userRepository.save(user);   // 3
                                }));
    }

    @Override
    public Mono<Long> deleteByUsername(String username) {
        return userRepository.deleteByUsername(username);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }
}
