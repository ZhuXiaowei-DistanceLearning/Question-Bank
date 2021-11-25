package com.zxw.designpattern.status;

import com.zxw.designpattern.status.checker.Checker;

import java.util.Collections;
import java.util.List;

/**
 * 状态机校验器
 */
public interface Checkable {
    /**
     * 参数校验
     */
    default List<Checker> getParamChecker() {
        return Collections.EMPTY_LIST;
    }
    /**
     * 需同步执行的状态检查器
     */
    default List<Checker> getSyncChecker() {
        return Collections.EMPTY_LIST;
    }
    /**
     * 可异步执行的校验器
     */
    default List<Checker> getAsyncChecker() {
        return Collections.EMPTY_LIST;
    }
}