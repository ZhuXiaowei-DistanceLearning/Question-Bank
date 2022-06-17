package com.zxw.designpattern.tree;

import java.util.List;
import java.util.Map;

public interface LogicFilter {
    /**
     * 逻辑决策器
     *
     * @param matterValue          决策值
     * @param treeNodeLineInfoList 决策节点
     * @return 下⼀个节点Id
     */
    Long filter(String matterValue, List<TreeNodeLink>
            treeNodeLineInfoList);

    /**
     * 获取决策值
     *
     * @param decisionMatter 决策物料
     * @return 决策值
     */
    String matterValue(Long treeId, String userId, Map<String, String>
            decisionMatter);
}