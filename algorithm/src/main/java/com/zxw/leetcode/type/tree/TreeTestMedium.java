package com.zxw.leetcode.type.tree;

import com.zxw.datastruct.Node;
import com.zxw.datastruct.TreeNode;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * BFS（Breadth First Search）广度优先搜索,先进先出层级排列
 * DFS（Depth First Search）深度优先搜索，递归
 */
@Slf4j
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
        TreeTestMedium treeTestMedium = new TreeTestMedium();
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
        TreeNode tree = TreeOperation.createTree(new Integer[]{1, 2, 3, 4, null, null, 5});
        show = tree;
        TreeOperation.show(tree);
        treeTestMedium.zigzagLevelOrder(tree);
        treeTestMedium.pathSum(tree, 3);
    }

    /**
     * [117]填充每个节点的下一个右侧节点指针 II
     * @param root
     * @return
     */
    public Node connect(Node root) {
        return null;
    }

    /**
     * [113]路径总和 II
     *
     * @param root
     * @param targetSum
     * @return
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        pathSumDFS(res, list, root, targetSum);
        return res;
    }

    public void pathSumDFS(List<List<Integer>> res, List<Integer> list, TreeNode root, int targetSum) {
        if (root == null) {
            return;
        }
        list.add(root.val);
        if(root.left == null && root.right == null && targetSum-root.val == 0){
            List<Integer> dest = new ArrayList(Arrays.asList(new Integer[list.size()]));
            Collections.copy(dest,list);
            res.add(dest);
        }
        pathSumDFS(res, list, root.left, targetSum - root.val);
        pathSumDFS(res, list, root.right, targetSum - root.val);
        list.remove(list.size() - 1);
    }

    /**
     * [105]从前序与中序遍历序列构造二叉树
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return null;
    }

    private TreeNode buildTreeHelper(int[] preorder, int p_start, int p_end, int[] inorder, int i_start, int i_end) {
        // preorder 为空，直接返回 null
        if (p_start == p_end) {
            return null;
        }
        int root_val = preorder[p_start];
        TreeNode root = new TreeNode(root_val);
        //在中序遍历中找到根节点的位置
        int i_root_index = 0;
        for (int i = i_start; i < i_end; i++) {
            if (root_val == inorder[i]) {
                i_root_index = i;
                break;
            }
        }
        int leftNum = i_root_index - i_start;
        //递归的构造左子树
        root.left = buildTreeHelper(preorder, p_start + 1, p_start + leftNum + 1, inorder, i_start, i_root_index);
        //递归的构造右子树
        root.right = buildTreeHelper(preorder, p_start + leftNum + 1, p_end, inorder, i_root_index + 1, i_end);
        return root;
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
     * [103]二叉树的锯齿形程序遍历
     *
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        boolean t = true;
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (poll.left != null) queue.add(poll.left);
                if (poll.right != null) queue.add(poll.right);
                if (t) {
                    list.add(poll.val);
                } else {
                    list.add(0, poll.val);
                }
            }
            t = !t;
            res.add(list);
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