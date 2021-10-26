package com.zxw.event;

import com.zxw.constants.RedisKeyConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zxw
 * @date 2021/10/26 11:43
 */
@Component
public class ReadyQueueThread {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void init() {
        executorService.execute(()->{
            String jobId = redisTemplate.opsForList().rightPop(RedisKeyConsts.READY_QUEUE);
            Object job = redisTemplate.opsForHash().get(RedisKeyConsts.JOB_POOL, jobId);
        });
    }
}
