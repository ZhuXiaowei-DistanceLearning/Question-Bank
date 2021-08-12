package com.zxw.base;

import com.zxw.factory.BaseHandler;

/**
 * @author zxw
 * @date 2021/8/12 15:32
 */
public interface ConsumerHandler<T> extends BaseHandler {
    void receiveMessage(T t);
}
