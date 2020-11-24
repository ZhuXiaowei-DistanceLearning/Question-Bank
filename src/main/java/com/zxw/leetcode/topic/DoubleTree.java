package com.zxw.leetcode.topic;

import com.zxw.common.datastruct.Node;
import com.zxw.common.datastruct.TreeNode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author zxw
 * @date 2020/11/9 10:45
 */
public class DoubleTree {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        byte[] arr = new byte[1024 * 1024];
        arr = new byte[1024 * 1024];
        arr = new byte[1024 * 1024];
        arr = null;
        byte[] arr2 = new byte[1024 * 1024 * 2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 1000000; i++) {
            map.put(i, i);
        }
    }

    /**
     * 226. 翻转二叉树
     * 翻转一棵二叉树。
     * 示例：
     * 输入：
     * 4
     * /   \
     * 2     7
     * / \   / \
     * 1   3 6   9
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        invertTreeBFS(root);
        return root;
    }

    public void invertTreeBFS(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        invertTree(root.left);
        invertTree(root.right);
    }

    /**
     * 116. 填充每个节点的下一个右侧节点指针
     * 给定一个完美二叉树，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
     * <p>
     * struct Node {
     * int val;
     * Node *left;
     * Node *right;
     * Node *next;
     * }
     *
     * @param root
     * @return
     */
    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        connectBFS(root.left, root.right);
        return root;
    }

    public void connectBFS(Node n1, Node n2) {
        if (n1 == null || n2 == null) {
            return;
        }
        n1.next = n2;
        connectBFS(n1.left, n1.right);
        connectBFS(n1.right, n2.left);
        connectBFS(n2.left, n2.right);
    }

    /**
     * 114. 二叉树展开为链表
     * 给定一个二叉树，原地将它展开为一个单链表。
     * <p>
     * <p>
     * <p>
     * 例如，给定二叉树
     * <p>
     * 1
     * / \
     * 2   5
     * / \   \
     * 3   4   6
     *
     * @param root
     */
    public void flatten(TreeNode root) {
        if (root == null) return;

        flatten(root.left);
        flatten(root.right);

        /**** 后序遍历位置 ****/
        // 1、左右子树已经被拉平成一条链表
        TreeNode left = root.left;
        TreeNode right = root.right;

        // 2、将左子树作为右子树
        root.left = null;
        root.right = left;

        // 3、将原先的右子树接到当前右子树的末端
        TreeNode p = root;
        while (p.right != null) {
            p = p.right;
        }
        p.right = right;
    }

    /**
     * 654. 最大二叉树
     * 给定一个不含重复元素的整数数组。一个以此数组构建的最大二叉树定义如下：
     * 二叉树的根是数组中的最大元素。
     * 左子树是通过数组中最大值左边部分构造出的最大二叉树。
     * 右子树是通过数组中最大值右边部分构造出的最大二叉树。
     * 通过给定的数组构建最大二叉树，并且输出这个树的根节点。
     * 示例 ：
     * 输入：[3,2,1,6,0,5]
     * 输出：返回下面这棵树的根节点：
     * <p>
     * 6
     * /   \
     * 3     5
     * \    /
     * 2  0
     * \
     * 1
     *
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        int max = nums[0];
        int index = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > index) {
                max = nums[i];
                index = i;
            }
        }
        TreeNode treeNode = new TreeNode(max);
        treeNode.left = constructMaximumBinaryTree(Arrays.copyOfRange(nums, 0, index));
        treeNode.right = constructMaximumBinaryTree(Arrays.copyOfRange(nums, index + 1, nums.length));
        return treeNode;
    }
}
