package com.zxw.leetcode.type.tree;

import com.zxw.common.datastruct.TreeNode;
import com.zxw.leetcode.type.tree.TreeOperation;
import jnr.ffi.annotations.In;
import org.jruby.ast.DAsgnNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * BFS（Breadth First Search）广度优先搜索,先进先出层级排列
 * DFS（Depth First Search）深度优先搜索，递归
 */
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
     * [107]二叉树的层序遍历 II
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                list.add(poll.val);
                if (poll.left != null) queue.add(poll.left);
                if (poll.right != null) queue.add(poll.right);
            }
            res.add(0, list);
        }
        return res;
    }

    /**
     * [102]二叉树的层序遍历
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                list.add(poll.val);
                if (poll.left != null) queue.add(poll.left);
                if (poll.right != null) queue.add(poll.right);
            }
            res.add(list);
        }
        return res;
    }

    /**
     * [98]验证二叉搜索树
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode root, long minVal, long maxVal) {
        if (root == null)
            return true;
        //每个节点如果超过这个范围，直接返回false
        if (root.val >= maxVal || root.val <= minVal)
            return false;
        //这里再分别以左右两个子节点分别判断，
        //左子树范围的最小值是minVal，最大值是当前节点的值，也就是root的值，因为左子树的值要比当前节点小
        //右子数范围的最大值是maxVal，最小值是当前节点的值，也就是root的值，因为右子树的值要比当前节点大
        return isValidBST(root.left, minVal, root.val) && isValidBST(root.right, root.val, maxVal);
    }

    /**
     * [96]不同的二叉搜索树
     *
     * @param n
     * @return
     */
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i < n + 1; i++)
            for (int j = 1; j < i + 1; j++)
                dp[i] += dp[j - 1] * dp[i - j];
        return dp[n];
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