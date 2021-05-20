package com.zxw.designpattern.status.checker;

import com.zxw.designpattern.status.ServiceResult;
import com.zxw.designpattern.status.context.StateContext;

/**
 * @author zxw
 * @date 2021/5/20 16:45
 */
public class UnFinishChecker implements Checker {
    @Override
    public ServiceResult check(StateContext context) {
        return null;
    }
}
