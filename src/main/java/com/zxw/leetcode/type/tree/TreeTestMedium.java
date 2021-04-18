package com.zxw.leetcode.type.tree;

import com.zxw.common.datastruct.TreeNode;
import com.zxw.leetcode.type.tree.TreeOperation;
import jnr.ffi.annotations.In;

import java.util.ArrayList;
import java.util.List;

public class TreeTestMedium {
    static TreeNode show;
    TreeNode prevNode;
    TreeNode nodeRes;
    int max = 0;
    int sum = 0;
    int min = 99999;
    StringBuilder res = new StringBuilder();
    List<Integer> listRes = new ArrayList<>();

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(3);
        TreeNode t3 = new TreeNode(4);
        TreeNode t4 = new TreeNode(2);
        TreeNode t5 = new TreeNode(3);
        TreeNode t6 = new TreeNode(4);
        TreeNode t7 = new TreeNode(2);
        t5.left = t6;
        t5.right = t3;
        t4.left = t5;
        t4.right = t2;
        treeNode.left = t4;
        treeNode.right = t7;
        TreeNode tree = TreeOperation.createTree(new Integer[]{4, -7, -3, null, null, -9, -3, 9, -7, -4, null, 6, null, -6, -6, null, null, 0, 6, 5, null, 9, null, null, -1, -4, null, null, null, -2});
        show = tree;
        TreeOperation.show(treeNode);
    }

    /**
     * [96]不同的二叉搜索树
     *
     * @param n
     * @return
     */
    public int numTrees(int n) {
        
        return 0;
    }

    /**
     * [95]不同的二叉搜索树 II
     *
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        return null;
    }

    /**
     * [94]二叉树的中序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        inorderTraversalBFS(root, list);
        return list;
    }

    public void inorderTraversalBFS(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        inorderTraversalBFS(root.left, list);
        list.add(root.val);
        inorderTraversalBFS(root.right, list);
    }
}