package com.zxw.leetcode.topic;

import com.zxw.datastruct.TreeNode;
import com.zxw.leetcode.type.tree.LeetCodeWrapper;

/**
 * @author zxw
 * @date 2022/7/12 11:25
 */
public class BstTree {
    public static void main(String[] args) {
        BstTree bstTree = new BstTree();
        bstTree.kthSmallest(LeetCodeWrapper.stringToTreeNode("[4,2,5,null,3]"), 1);

    }

    int res = 0;

    public int kthSmallest(TreeNode root, int k) {
        int rank = 0;
        dfs(root, k, rank);
        return res;
    }

    public void dfs(TreeNode root, int k, int rank) {
        if (root == null) {
            return;
        }
        dfs(root.left, k, rank);
        rank++;
        if (rank == k) {
            res = k;
            return;
        }
        dfs(root.right, k, rank);
    }

}
