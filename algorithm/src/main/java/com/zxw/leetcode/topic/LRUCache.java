package com.zxw.leetcode.topic;

import cn.hutool.core.lang.Assert;

import java.util.LinkedHashMap;

class LRUCache {
    int capacity;
    int alloc;
    LinkedHashMap<Integer, Integer> map;

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);
        Assert.isTrue(lruCache.get(2) == -1);
        lruCache.put(2, 5);
        Assert.isTrue(lruCache.get(1) == -1);
        lruCache.put(1, 5);
        lruCache.put(1, 2);
        Assert.isTrue(lruCache.get(1) == 2);
        Assert.isTrue(lruCache.get(2) == 6);
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new LinkedHashMap<>();
    }

    /**
     * 1.如果不存在则返回-1
     * 2.如果存在则从链表中去除，并加入队尾
     *
     * @param key
     * @return
     */
    public int get(int key) {
        Integer res = map.get(key);
        if (res == null) {
            return -1;
        }
        map.remove(key);
        map.put(key, res);
        return res;
    }

    /**
     * 如果存在，则删除该key，并把他加入到末尾
     * 如果不存在，判断长度并且加入队尾
     *
     * @param key
     * @param value
     */
    public void put(int key, int value) {
        Integer integer = map.get(key);
        if (integer != null) {
            map.remove(key);
            map.put(key, value);
            return;
        }
        if (map.size() >= capacity) {
            Integer next = map.keySet().iterator().next();
            map.remove(next);
        }
        map.put(key, value);
    }
}