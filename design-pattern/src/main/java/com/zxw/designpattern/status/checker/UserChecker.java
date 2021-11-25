package com.zxw.designpattern.status.checker;

import com.zxw.designpattern.status.context.StateContext;
import com.zxw.designpattern.status.ServiceResult;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2021/5/20 16:45
 */
@Component
public class UserChecker implements Checker {
    @Override
    public ServiceResult check(StateContext context) {
        return null;
    }
}
