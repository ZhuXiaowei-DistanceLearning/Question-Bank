package com.zxw.designpattern.pipe;

import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2020-12-14 22:10
 */
@Component
public class InputDataPreChecker implements ContextHandler<InstanceBuildContext> {
    @Override
    public boolean handler(InstanceBuildContext context) {
        return false;
    }
}
