package com.zxw.repository;

import com.zxw.entity.MyEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MyEventRepository extends ReactiveMongoRepository<MyEvent, Long> { // 1
}