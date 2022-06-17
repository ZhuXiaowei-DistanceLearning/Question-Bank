package com.zxw.designpattern.tree;

import lombok.Data;

@Data
public class TreeRoot {

    private Long treeId;         //规则树ID
    private Long treeRootNodeId; //规则树根ID
    private String treeName;     //规则树名称
}