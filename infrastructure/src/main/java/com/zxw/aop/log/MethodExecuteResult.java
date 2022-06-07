package com.zxw.aop.log;

import lombok.Data;

/**
 * @author zxw
 * @date 2022/6/7 22:59
 */
@Data
public class MethodExecuteResult {
    public Throwable throwable;

    public MethodExecuteResult(boolean b, Object o, String s) {

    }

    public Object isSuccess() {
        return null;
    }

    public Object getErrorMsg() {
        return null;
    }
}
