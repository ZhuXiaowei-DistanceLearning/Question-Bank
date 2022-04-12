package com.zxw.enginetree.handler;

import com.zxw.enginetree.EngineContext;
import com.zxw.enginetree.EngineResult;

/**
 * @author zxw
 * @date 2022/4/10 22:21
 */
public abstract class DefaultEngineHandler implements EngineHandler {

    @Override
    public boolean handler() {
        return true;
    }

    abstract EngineResult action(EngineContext context);
}
