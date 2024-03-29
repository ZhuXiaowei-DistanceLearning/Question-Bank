package com.zxw.leetcode.topic;

import com.zxw.datastruct.Node;
import com.zxw.datastruct.TreeNode;
import com.zxw.leetcode.type.tree.LeetCodeWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zxw
 * @date 2022/7/15 10:07
 */
public class Tree {
    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.buildTree(LeetCodeWrapper.stringToIntegerArray("[1,2,3]"), LeetCodeWrapper.stringToIntegerArray("[3,2,1]"));
//        tree.buildTree(LeetCodeWrapper.stringToIntegerArray("[3,9,20,15,7]"), LeetCodeWrapper.stringToIntegerArray("[9,3,15,20,7]"));
//        tree.constructMaximumBinaryTree(LeetCodeWrapper.stringToIntegerArray("[3,2,1,6,0,5]"));
//        tree.levelOrder(LeetCodeWrapper.stringToTreeNode("[3,9,20,null,null,15,7]"));
//        Assert.isTrue(tree.isSymmetric(LeetCodeWrapper.stringToTreeNode("[1,2,2,3,4,4,3]")));
//        Assert.isTrue(!tree.isSymmetric(LeetCodeWrapper.stringToTreeNode("[1,2,2,null,3,null,3]")));

    }

    /**
     * 114. 二叉树展开为链表
     * @param root
     */
    public void flatten(TreeNode root) {
        if(root == null){
            return;
        }
        flatten(root.left);
        flatten(root.right);
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.right = left;
        root.left = null;
        TreeNode p = root;
        while(p.right != null){
            p = p.right;
        }
        p.right = right;
    }

    /**
     * 1、是否可以通过遍历一遍二叉树得到答案？如果可以，用一个 traverse 函数配合外部变量来实现，这叫「遍历」的思维模式。
     * 2、是否可以定义一个递归函数，通过子问题（子树）的答案推导出原问题的答案？如果可以，写出这个递归函数的定义，并充分利用这个函数的返回值，这叫「分解问题」的思维模式。
     * 无论使用哪种思维模式，你都需要思考：
     *
     * 如果单独抽出一个二叉树节点，它需要做什么事情？需要在什么时候（前/中/后序位置）做？其他的节点不用你操心，递归函数会帮你在所有节点上执行相同的操作。
     * @param root
     * @return
     */
    public Node connect(Node root) {
        if(root == null){
            return null;
        }
        dfs(root.left, root.right);
        return root;
    }

    public void dfs(Node left,Node right) {
        if(left == null || right == null){
            return;
        }
        left.next = right;
        dfs(left.left,left.right);
        dfs(left.right,right.left);
        dfs(right.left,right.right);
    }

    /**
     * 105. 从前序与中序遍历序列构造二叉树
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree(preorder, 0, preorder.length, inorder, 0, inorder.length);
    }

    public TreeNode buildTree(int[] preorder, int plo, int phi, int[] inorder, int ilo, int ihi) {
        if (preorder.length == 0 || inorder.length == 0) {
            return null;
        }
        if (ilo >= ihi) {
            return null;
        }
        if(plo >= phi){
            return null;
        }
        int index = ilo;
        for (int i = ilo; i < ihi; i++) {
            if (inorder[i] == preorder[plo]) {
                index = i;
                break;
            }
        }
        TreeNode treeNode = new TreeNode(preorder[plo]);
        // phi：inorder的长度 + 起始位置
        // 求左子树的长度以及右子树的长度
        treeNode.left = buildTree(preorder, plo + 1, plo + index - ilo + 1, inorder, ilo, index);
        treeNode.right = buildTree(preorder, plo + index - ilo + 1, phi, inorder, index + 1, ihi);
        return treeNode;
    }


    /**
     * 654. 最大二叉树
     *
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums.length == 0) {
            return null;
        }
        return constructMaximumBinaryTree(nums, 0, nums.length);
    }

    public TreeNode constructMaximumBinaryTree(int[] nums, int lo, int hi) {
        if (nums.length == 0) {
            return null;
        }
        if (lo >= hi) {
            return null;
        }
        int max = nums[lo];
        int index = lo;
        for (int i = lo; i < hi; i++) {
            if (nums[i] > max) {
                max = nums[i];
                index = i;
            }
        }
        TreeNode node = new TreeNode(max);
        node.left = constructMaximumBinaryTree(nums, lo, index);
        node.right = constructMaximumBinaryTree(nums, index + 1, hi);
        return node;
    }

    /**
     * 102.二叉树的层序遍历
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            res.add(list);
        }
        return res;
    }

    /**
     * 101.对称二叉树
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return false;
        }
        return isSymmetricDfs(root.left, root.right);
    }

    public boolean isSymmetricDfs(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null && right != null) {
            return false;
        }
        if (left != null && right == null) {
            return false;
        }
        if (left.val != right.val) {
            return false;
        }
        return isSymmetricDfs(left.left, right.right) && isSymmetricDfs(left.right, right.left);
    }
}
