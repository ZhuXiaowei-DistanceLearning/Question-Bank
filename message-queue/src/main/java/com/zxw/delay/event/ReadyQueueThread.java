package com.zxw.delay.event;

import com.zxw.constants.RedisKeyConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zxw
 * @date 2021/10/26 11:43
 */
//@Component
public class ReadyQueueThread {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void init() {
        executorService.execute(() -> {
            while (true) {
                String jobId = redisTemplate.opsForList().rightPop(RedisKeyConsts.READY_QUEUE,1, TimeUnit.SECONDS);
                Object job = redisTemplate.opsForHash().get(RedisKeyConsts.JOB_POOL, jobId);
            }
        });
    }
}
