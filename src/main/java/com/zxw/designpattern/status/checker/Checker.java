package com.zxw.designpattern.status.checker;

import com.zxw.designpattern.status.ServiceResult;
import com.zxw.designpattern.status.context.StateContext;

/**
 * checker的定位是校验器，负责校验参数或业务的合法性，但实际编码过程中、checker中可能会有一些临时状态类操作，比如在校验之前进行计数或者加锁操作、在校验完成后根据结果进行释放，这里就需要支持统一的释放功能。
 * 状态机校验器
 */
public interface Checker<T, C> {
    ServiceResult<T,C> check(StateContext<C> context);
    /**
     * 是否需求release
     */
    default boolean needRelease() {
        return false;
    }
    /**
     * 业务执行完成后的释放方法,
     * 比如有些业务会在checker中加一些状态操作，等业务执行完成后根据结果选择处理这些状态操作,
     * 最典型的就是checker中加一把锁，release根据结果释放锁.
     */
    default void release(StateContext<C> context, ServiceResult<T,C> result) {
    }
    /**
     * 多个checker时的执行顺序
     */
    default int order() {
        return 0;
    }
}