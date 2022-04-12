package com.zxw.enginetree;

import com.zxw.enginetree.handler.DefaultEngineHandler;

import java.util.Objects;

/**
 * @author zxw
 * @date 2022/4/10 22:05
 */
public abstract class DefaultEngineTreeHandler {

    public void handler(EngineTree engineTree) {
        if (Objects.nonNull(engineTree)) {
            try {
                try {
                    DefaultEngineHandler engineHandler = (DefaultEngineHandler) Class.forName(engineTree.getHandlerName()).newInstance();
                    boolean handler = engineHandler.handler();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
