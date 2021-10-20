package com.zxw.cache.redis;

/**
 * @author zxw
 * @date 2021/9/2 14:05
 */
public interface CacheService {
    public void lock(String key);

    public void unLock(String key);
}
