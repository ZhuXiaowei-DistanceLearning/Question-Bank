package com.zxw.book.cache.store;

/**
 * @author zxw
 * @date 2021-03-22 21:34
 */
public interface DataStore<K, V> {
    ValueHolder<V> get(K key) throws StoreAccessException;

    ValueHolder<V> put(K key, V value) throws StoreAccessException;

    ValueHolder<V> remove(K key) throws StoreAccessException;

    void clear() throws StoreAccessException;
}
