package com.zxw.book.cache.store.impl;

import com.zxw.book.cache.store.DataStore;
import com.zxw.book.cache.store.StoreAccessException;
import com.zxw.book.cache.store.ValueHolder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author zxw
 * @date 2021-03-22 21:36
 */
public class WeekDataStore<K,V> implements DataStore<K,V> {
    ConcurrentMap<K, ValueHolder<V>> map = new ConcurrentHashMap<>();

    @Override
    public ValueHolder<V> get(K key) throws StoreAccessException {
        return null;
    }

    @Override
    public ValueHolder<V> put(K key, V value) throws StoreAccessException {
        return null;
    }

    @Override
    public ValueHolder<V> remove(K key) throws StoreAccessException {
        return null;
    }

    @Override
    public void clear() throws StoreAccessException {

    }
}
