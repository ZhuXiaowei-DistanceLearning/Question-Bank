package com.zxw.condition;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
class RWDictionary {
    private final Map<String, String> m = new TreeMap<>();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public String get(String key) {
        r.lock();
        try {
            Thread.sleep(5000);
            return m.get(key);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            r.unlock();
        }
    }

    public List<String> allKeys() {
        r.lock();
        try {
            return new ArrayList<>(m.keySet());
        } finally {
            r.unlock();
        }
    }

    public String put(String key, String value) {
        w.lock();
        String val = "";
        try {
            log.info("获取到写锁");
            val = m.put(key, value);
        } finally {
            w.unlock();
        }
        log.info("结束");
        return val;
    }

    public void clear() {
        w.lock();
        try {
            m.clear();
        } finally {
            w.unlock();
        }
    }
}