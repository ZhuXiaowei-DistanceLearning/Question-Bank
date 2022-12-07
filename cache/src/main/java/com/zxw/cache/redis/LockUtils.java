package com.zxw.cache.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author zxw
 * @date 2022/4/26 10:16
 */
@Slf4j
@Component
public class LockUtils {
    @Autowired
    private RedissonClient redisson;

    public void lock(String keyName, Runnable runnable) {
        RLock lock = redisson.getLock(keyName);
        lock.lock();
        try {
            log.info("分布式锁获取,线程:{},key:{}", Thread.currentThread().getName(), keyName);
            runnable.run();
        } finally {
            lock.unlock();
            log.info("分布式锁释放,线程:{},key:{}", Thread.currentThread().getName(), keyName);
        }
    }

    public Object lock(String keyName, Supplier<Object> runnable) {
        RLock lock = redisson.getLock(keyName);
        lock.tryLock();
        try {
            return runnable.get();
        } finally {
            lock.unlock();
        }
    }

    public Object lock(boolean condition, String keyName, Supplier<Object> runnable) {
        if (condition) {
            return runnable.get();
        }
        RLock lock = redisson.getLock(keyName);
        lock.tryLock();
        try {
            return runnable.get();
        } finally {
            lock.unlock();
        }
    }

}
