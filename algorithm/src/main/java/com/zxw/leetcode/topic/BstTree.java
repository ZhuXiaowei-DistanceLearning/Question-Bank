package com.zxw.leetcode.topic;

import cn.hutool.core.lang.Assert;
import com.zxw.datastruct.TreeNode;

/**
 * @author zxw
 * @date 2022/7/12 11:25
 */
public class BstTree {
    public static void main(String[] args) {
        BstTree bstTree = new BstTree();
        Assert.isTrue(bstTree.numTrees(3) == 5);
//        bstTree.deleteNode(LeetCodeWrapper.stringToTreeNode("[5,3,6,2,4,null,7]"), 5);
//        bstTree.insertIntoBST(LeetCodeWrapper.stringToTreeNode("[6,4,7,1,3]"), 5);
//        bstTree.searchBST(LeetCodeWrapper.stringToTreeNode("[4,2,7,1,3]"), 2);
//        bstTree.isValidBST(LeetCodeWrapper.stringToTreeNode("[5,1,9,null,null,10,6]"));
//        bstTree.bstToGst(LeetCodeWrapper.stringToTreeNode("[4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]"));
//        bstTree.kthSmallest(LeetCodeWrapper.stringToTreeNode("[4,2,5,null,3]"), 1);
    }

    int sum = 0;

    /**
     * 96. 不同的二叉搜索树
     *
     * @param n
     * @return
     */
    public int numTrees(int n) {
        int[][] dp = new int[n + 1][n + 1];
        return getNumTreeCount(1, n, dp);
    }

    int getNumTreeCount(int lo, int hi, int[][] dp) {

        int res = 0;
        if (lo > hi) {
            return 1;
        }
        if (dp[lo][hi] != 0) {
            return dp[lo][hi];
        }
        for (int i = lo; i <= hi; i++) {
            int left = getNumTreeCount(lo, i - 1, dp);
            int right = getNumTreeCount(i + 1, hi, dp);
            res += left * right;
        }
        dp[lo][hi] = res;
        return res;
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {

        }
        // 这里要进行情况分析
        if (root.val == key) {
            // 两个都为空
            if (root.right == null && root.left == null) {
                return null;
            }
            // 左右子树一个为空
            if (root.right == null) {
                return root.left;
            }
            if (root.left == null) {
                return root.right;
            }
            // 两个都不为空，找到左子树的最大或者右子树的最小
            // 处理情况 3
            // 获得右子树最小的节点
            TreeNode minNode = getMin(root.right);
            // 删除右子树最小的节点
            root.right = deleteNode(root.right, minNode.val);
            // 用右子树最小的节点替换 root 节点
            minNode.left = root.left;
            minNode.right = root.right;
            root = minNode;

        }
        if (root.val > key) {
            root.left = deleteNode(root.left, key);
        }
        if (root.val < key) {
            root.right = deleteNode(root.right, key);
        }
        return root;
    }

    private TreeNode getMin(TreeNode node) {
        // BST 最左边的就是最小的
        while (node.left != null) node = node.left;
        return node;
    }

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
