package com.zxw.event;

import com.zxw.constants.RedisKeyConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zxw
 * @date 2021/10/26 10:12
 */
@Component
@Slf4j
public class BucketThread {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void init() {
        executorService.execute(() -> {
            while (true) {
                Set<String> queue = redisTemplate.opsForZSet().rangeByScore(RedisKeyConsts.DELAY_BUCKET, 0, System.currentTimeMillis(), 0, 1);
                if (CollectionUtils.isNotEmpty(queue)) {
                    queue.forEach(k -> {
                        redisTemplate.opsForList().leftPush(RedisKeyConsts.READY_QUEUE,k);
                    });
                }
            }
        });
    }
}
