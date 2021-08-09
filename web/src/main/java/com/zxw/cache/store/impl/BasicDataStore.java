package com.zxw.cache.store.impl;

import com.zxw.cache.store.DataStore;
import com.zxw.cache.store.StoreAccessException;
import com.zxw.cache.store.ValueHolder;

/**
 * @author zxw
 * @date 2021-03-22 21:36
 */
public class BasicDataStore implements DataStore {
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
