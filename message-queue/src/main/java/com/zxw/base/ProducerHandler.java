package com.zxw.base;

import com.zxw.factory.BaseHandler;

/**
 * @author zxw
 * @date 2021/8/12 15:30
 */
public interface ProducerHandler extends BaseHandler {
    void sendMessage(String message);
}
