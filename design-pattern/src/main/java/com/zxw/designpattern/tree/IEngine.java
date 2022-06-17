package com.zxw.designpattern.tree;

import java.util.Map;

public interface IEngine {
    EngineResult process(final Long treeId, final String userId, TreeRich
            treeRich, final Map<String, String> decisionMatter);
}