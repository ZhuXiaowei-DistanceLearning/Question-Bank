package com.zxw.designpattern.pipe;

import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2020-12-14 22:11
 */
@Component
public class ModelInstanceCreator implements ContextHandler<ModelInstanceContext>{
    @Override
    public boolean handler(ModelInstanceContext context) {
        return false;
    }
}
