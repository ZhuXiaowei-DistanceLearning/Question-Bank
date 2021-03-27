package com.zxw.book.cache.store.impl;

import com.zxw.book.cache.store.DataStore;
import com.zxw.book.cache.store.StoreAccessException;
import com.zxw.book.cache.store.ValueHolder;

/**
 * @author zxw
 * @date 2021-03-22 21:36
 */
public class LRUDataStore implements DataStore {
    @Override
    public ValueHolder get(Object key) throws StoreAccessException {
        return null;
    }

    @Override
    public ValueHolder put(Object key, Object value) throws StoreAccessException {
        return null;
    }

    @Override
    public ValueHolder remove(Object key) throws StoreAccessException {
        return null;
    }

    @Override
    public void clear() throws StoreAccessException {

    }
}
