package com.zxw.designpattern.aop;

/**
 * @author zxw
 * @date 2021-01-20 22:44
 */
public interface MethodAspectHandler {
    /**
     * 获得切面绑定的方法增强处理器的类型
     */
    Class<? extends MethodAdviceHandler<?>> getAdviceHandlerType();
}
