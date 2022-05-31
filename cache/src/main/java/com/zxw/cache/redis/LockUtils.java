package com.zxw.cache.redis;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;

import java.util.Objects;

/**
 * @author zxw
 * @date 2022/4/26 10:16
 */
@Slf4j
public class LockUtils {
    private static Redisson redisson = null;

    public static void lock(String keyName, Runnable runnable) {
        if (Objects.isNull(redisson)) {
            redisson = SpringUtil.getBean(Redisson.class);
        }
        RLock lock = redisson.getLock(keyName);
        lock.lock();
        try {
            runnable.run();
        } finally {
            lock.unlock();
        }
    }
}
