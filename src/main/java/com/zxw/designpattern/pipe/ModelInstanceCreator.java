package com.zxw.designpattern.pipe;

import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2020-12-14 22:11
 */
@Component
public class ModelInstanceCreator implements ContextHandler<InstanceBuildContext>{
    @Override
    public boolean handler(InstanceBuildContext context) {
        return false;
    }
}
