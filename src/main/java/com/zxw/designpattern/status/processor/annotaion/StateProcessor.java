package com.zxw.designpattern.status.processor.annotaion;

import com.zxw.designpattern.status.ServiceResult;
import com.zxw.designpattern.status.context.StateContext;

/**
 * 状态机处理器接口
 */
public interface StateProcessor<T, C> {
    /**
     * 执行状态迁移的入口
     */
    ServiceResult<T> action(StateContext<C> context) throws Exception;
}