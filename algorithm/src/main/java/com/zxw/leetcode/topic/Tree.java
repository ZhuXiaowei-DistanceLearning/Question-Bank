package com.zxw.leetcode.topic;

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
        tree.levelOrder(LeetCodeWrapper.stringToTreeNode("[3,9,20,null,null,15,7]"));
//        Assert.isTrue(tree.isSymmetric(LeetCodeWrapper.stringToTreeNode("[1,2,2,3,4,4,3]")));
//        Assert.isTrue(!tree.isSymmetric(LeetCodeWrapper.stringToTreeNode("[1,2,2,null,3,null,3]")));

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
