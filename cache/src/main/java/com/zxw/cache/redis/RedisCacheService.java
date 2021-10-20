package com.zxw.cache.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2021/9/2 14:06
 */
@Component
public class RedisCacheService implements CacheService {
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void lock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.lock();
    }

    @Override
    public void unLock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }
}
