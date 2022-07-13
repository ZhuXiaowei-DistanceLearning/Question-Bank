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
        bstTree.insertIntoBST(LeetCodeWrapper.stringToTreeNode("[6,4,7,1,3]"), 5);
//        bstTree.searchBST(LeetCodeWrapper.stringToTreeNode("[4,2,7,1,3]"), 2);
//        bstTree.isValidBST(LeetCodeWrapper.stringToTreeNode("[5,1,9,null,null,10,6]"));
//        bstTree.bstToGst(LeetCodeWrapper.stringToTreeNode("[4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]"));
//        bstTree.kthSmallest(LeetCodeWrapper.stringToTreeNode("[4,2,5,null,3]"), 1);
    }

    int sum = 0;

    public TreeNode insertIntoBST(TreeNode root, int val) {
        // 找到空位置插入新节点
        if (root == null) return new TreeNode(val);
        // if (root.val == val)
        //     BST 中一般不会插入已存在元素
        if (root.val < val)
            root.right = insertIntoBST(root.right, val);
        if (root.val > val)
            root.left = insertIntoBST(root.left, val);
        return root;
    }

    /**
     * 700. 二叉搜索树中的搜索
     *
     * @param root
     * @param val
     * @return
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        }
        if (root.val < val) {
            return searchBST(root.right, val);
        } else {
            return searchBST(root.left, val);
        }
    }


    /**
     * 98. 验证二叉搜索树
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBSTDFS(root, null, null);
    }

    public boolean isValidBSTDFS(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null) {
            return true;
        }
        if (min != null && min.val >= root.val) return false;
        if (max != null && max.val <= root.val) return false;
        return isValidBSTDFS(root.left, min, root) && isValidBSTDFS(root.right, root, max);
    }

    /**
     * 1038. 从二叉搜索树到更大和树
     *
     * @param root
     * @return
     */
    public TreeNode bstToGst(TreeNode root) {
        if (root == null) {
            return null;
        }
        bstToGst(root.right);
        sum += root.val;
        root.val = sum;
        bstToGst(root.left);
        return root;
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
