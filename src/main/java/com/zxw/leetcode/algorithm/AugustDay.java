package com.zxw.leetcode.algorithm;

import com.zxw.common.datastruct.TreeNode;

/**
 * @author zxw
 * @date 2020/8/7 9:21
 */
public class AugustDay {
    public static void main(String[] args) {
        AugustDay augustDay = new AugustDay();
    }

    /**
     * 100. 相同的树
     * 给定两个二叉树，编写一个函数来检验它们是否相同。
     * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
     * 示例 1:
     * 输入:       1         1
     *           / \       / \
     *          2   3     2   3
     *
     *         [1,2,3],   [1,2,3]
     * 输出: true
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q != null){ 
            return false;
        }
        if(p != null && q == null){
            return false;
        }
        if(p.val != q.val){
            return false;
        }
        if(p == null && q == null){
            return true;
        }
        return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }
}
