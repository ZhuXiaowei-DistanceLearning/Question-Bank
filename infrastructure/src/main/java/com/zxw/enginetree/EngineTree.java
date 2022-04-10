package com.zxw.enginetree;

import lombok.Data;

/**
 * @author zxw
 * @date 2022/4/10 21:45
 */
@Data
public class EngineTree {
    private String id;
    private String parentId;
    private String handlerName;
    private EngineTree left;
    private EngineTree right;
}
