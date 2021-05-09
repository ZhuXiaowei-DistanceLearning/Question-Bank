package com.zxw.netty.rpc;

/**
 * @author zxw
 * @date 2021-04-20 20:37
 */
public interface Serializer {
    <T> Object serialize(T obj);

    <T> Object deserialize(byte[] bytes, Class<T> clazz);
}
