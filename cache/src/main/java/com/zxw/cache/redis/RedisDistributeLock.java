package com.zxw.cache.redis;

import com.zxw.utils.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class RedisDistributeLock implements CacheService {
    private final Logger log = LoggerFactory.getLogger(RedisDistributeLock.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Environment environment;

    private Long MAX_RELEASE_TIME = 4L;

    private Map<String, Integer> cacheMap = new HashMap<>();

    private Lock cacheLock = new ReentrantLock(true);

    @Override
    public void lock(String key) {
        lockExpire(key, 0L, null);
    }

    public Boolean getLock(String key, Long expireTime, TimeUnit timeUnit) {
        return lockExpire(key, expireTime, timeUnit);
    }

    private boolean lockExpire(String key, Long expireTime, TimeUnit timeUnit) {
        try {
            String localIP = IPUtils.getIpAddr(null) + ":" + environment.getProperty("server.port") + ":" + Thread.currentThread().getId();
            String value = localIP + "@@" + System.currentTimeMillis();
            Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value);
            if (!success && !hasKey(localIP)) {
                Object var = redisTemplate.opsForValue().get(key);
                if (!Objects.isNull(var)) {
                    // localIp@@time
                    String[] arr = String.valueOf(var).split("@@");
                    if (!arr[0].equals(localIP) && System.currentTimeMillis() - Long.valueOf(arr[1]).longValue() > MAX_RELEASE_TIME) {
                        if (hasKey(arr[0])) {
                            this.cacheMap.remove(arr[0]);
                        }
                        redisTemplate.delete(key);
                    } else if (arr[0].equals(localIP)) {
                        putCache(localIP);
                        return true;
                    }
                }
                return false;
            }
            if (expireTime != null || expireTime > 0) {
                if (timeUnit == null) {
                    timeUnit = TimeUnit.SECONDS;
                }
                Boolean expire = redisTemplate.expire(key, expireTime, timeUnit);
                if (!expire) {
                    log.info("redis设置过期时间失败");
                    return false;
                }
            }
            putCache(localIP);
            return true;
        } catch (Exception e) {
            log.info("redis获取锁异常:{}", e);
            return false;
        }
    }

    @Override
    public void unLock(String key) {
        try {
            String localIP = IPUtils.getIpAddr(null);
            clearCache(localIP);
            if (!hasKey(localIP)) {
                redisTemplate.delete(key);
            }
        } catch (Exception e) {
            log.info("redis释放锁异常:{}", e);
        }
    }

    private void putCache(String key) {
        cacheLock.lock();
        try {
            Integer num = cacheMap.get(key);
            if (Objects.isNull(num)) {
                cacheMap.putIfAbsent(key, 1);
                return;
            }
            cacheMap.put(key, num + 1);
        } finally {
            cacheLock.unlock();
        }
    }

    private void clearCache(String key) {
        cacheLock.lock();
        try {
            Integer num = cacheMap.get(key);
            if (Objects.isNull(num)) {
                return;
            }
            num--;
            if (num == 0) {
                cacheMap.remove(key);
            } else {
                cacheMap.put(key, num);
            }
        } finally {
            cacheLock.unlock();
        }
    }

    public boolean hasKey(String key) {
        cacheLock.lock();
        try {
            return cacheMap.containsKey(key);
        } finally {
            cacheLock.unlock();
        }
    }
}
