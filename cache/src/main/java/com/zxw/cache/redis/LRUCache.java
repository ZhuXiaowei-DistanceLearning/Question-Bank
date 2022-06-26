package com.zxw.cache.redis;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {


    /**
     * cache = {5=hi, 6=hi, 7=hi, 8=hi, 9=hi}
     * hi
     * cache = {5=hi, 6=hi, 8=hi, 9=hi, 7=hi}
     * cache = {7=hi, 10=hi, 11=hi, 12=hi, 13=hi}
     * cache = {10=hi, 11=hi, 12=hi, 13=hi, 42=meaning of life}
     * @param args
     */
    public static void main(String[] args) {
        Map<Integer, String> cache = new LRUCache<>(5);
        for (int i = 0; i < 10; i++) {
            cache.put(i, "hi");
        }
        // entries 0-4 have already been removed
        // entries 5-9 are ordered
        System.out.println("cache = " + cache);

        System.out.println(cache.get(7));
        // entry 7 has moved to the end
        System.out.println("cache = " + cache);

        for (int i = 10; i < 14; i++) {
            cache.put(i, "hi");
        }
        // entries 5,6,8,9 have been removed (eldest entries)
        // entry 7 is at the beginning now
        System.out.println("cache = " + cache);

        cache.put(42, "meaning of life");
        // entry 7 is gone too
        System.out.println("cache = " + cache);
    }

    private final int maxEntries;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public LRUCache(int initialCapacity,
                    float loadFactor,
                    int maxEntries) {
        super(initialCapacity, loadFactor, true);
        this.maxEntries = maxEntries;
    }

    public LRUCache(int initialCapacity,
                    int maxEntries) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, maxEntries);
    }

    public LRUCache(int maxEntries) {
        this(DEFAULT_INITIAL_CAPACITY, maxEntries);
    }

    // not very useful constructor
    public LRUCache(Map<? extends K, ? extends V> m,
                    int maxEntries) {
        this(m.size(), maxEntries);
        putAll(m);
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxEntries;
    }
}