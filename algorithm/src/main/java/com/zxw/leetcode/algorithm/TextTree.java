package com.zxw.leetcode.algorithm;

import com.zxw.datastruct.TreeNode;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zxw
 * @date 2020/7/4 17:48
 */
public class TextTree {
    public static void main(String[] args) {
        TextTree textTree = new TextTree();
        while (true) {
            TreeNode root = new TreeNode(1);
//            System.out.println(Runtime.getRuntime().freeMemory());
        }
//        TreeNode root2 = new TreeNode(1);
//        TreeNode root3 = new TreeNode(1);
//        root.left = root2;
//        root.right = root3;
//        root2.left = new TreeNode(1);

//        textTree.convertBST(root);
//        textTree.mergeTrees(root, root);
//        textTree.isUnivalTree(root);
    }

    /**
     * 572. 另一个树的子树
     * 给定两个非空二叉树 s 和 t，检验 s 中是否包含和 t 具有相同结构和节点值的子树。s 的一个子树包括 s 的一个节点和这个节点的所有子孙。s 也可以看做它自身的一棵子树。
     * 示例 1:
     * 给定的树 s:
     * <p>
     * 3
     * / \
     * 4   5
     * / \
     * 1   2
     * 给定的树 t：
     * <p>
     * 4
     * / \
     * 1   2
     * 返回 true，因为 t 与 s 的一个子树拥有相同的结构和节点值。
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null) {
            return false;
        }
        return isSubtreeDFS(s, t) ? true : (isSubtree(s.left, t) || isSubtree(s.right, t));
    }

    public boolean isSubtreeDFS(TreeNode s, TreeNode t) {
        if (s == null && t == null) {
            return true;
        }
        if (s == null || t == null) {
            return false;
        }
        if (s.val != t.val) {
            return false;
        }
        return isSubtreeDFS(s.left, t.left) && isSubtreeDFS(s.right, t.right);
    }

    /**
     * 100. 相同的树
     * 给定两个二叉树，编写一个函数来检验它们是否相同。
     * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
     * 示例 1:
     * 输入:       1         1
     * / \       / \
     * 2   3     2   3
     * <p>
     * [1,2,3],   [1,2,3]
     * <p>
     * 输出: true
     *
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * 538. 把二叉搜索树转换为累加树
     * 给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，使得每个节点的值是原来的节点值加上所有大于它的节点值之和。
     * 例如：
     * 输入: 原始二叉搜索树:
     * 5
     * /   \
     * 2     13
     * 输出: 转换为累加树:
     * 18
     * /   \
     * 20     13
     *
     * @param root
     * @return
     */
    int sum = 0;

    public TreeNode convertBST(TreeNode root) {
        if (root != null) {
            convertBST(root.right);
            sum = sum + root.val;
            root.val = sum;
            convertBST(root.left);
            return root;
        }
        return root;
    }


    /**
     * 110. 平衡二叉树
     * 给定一个二叉树，判断它是否是高度平衡的二叉树。
     * 本题中，一棵高度平衡二叉树定义为：
     * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。
     * 示例 1:
     * 给定二叉树 [3,9,20,null,null,15,7]
     * <p>
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     * 返回 true 。
     *
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        return isBalancedDFS(root) != 1;
    }

    public int isBalancedDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = isBalancedDFS(root.left);
        if (left == -1) {
            return -1;
        }
        int right = isBalancedDFS(root.right);
        if (right == -1) {
            return -1;
        }
        return Math.abs(left - right) < 2 ? Math.max(left, right) + 1 : -1;
    }

    /**
     * 617. 合并二叉树
     * 给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。
     * <p>
     * 你需要将他们合并为一个新的二叉树。合并的规则是如果两个节点重叠，那么将他们的值相加作为节点合并后的新值，否则不为 NULL 的节点将直接作为新二叉树的节点。
     * <p>
     * 示例 1:
     * <p>
     * 输入:
     * Tree 1                     Tree 2
     * 1                         2
     * / \                       / \
     * 3   2                     1   3
     * /                           \   \
     * 5                             4   7
     * 输出:
     * 合并后的树:
     * 3
     * / \
     * 4   5
     * / \   \
     * 5   4   7
     *
     * @param t1
     * @param t2
     * @return
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null || t2 == null) {
            return t1 == null ? t2 : t1;
        }
        return dfs(t1, t2);
    }

    TreeNode dfs(TreeNode r1, TreeNode r2) {
        // 如果 r1和r2中，只要有一个是null，函数就直接返回
        if (r1 == null || r2 == null) {
            return r1 == null ? r2 : r1;
        }
        //让r1的值 等于  r1和r2的值累加，再递归的计算两颗树的左节点、右节点
        r1.val += r2.val;
        r1.left = dfs(r1.left, r2.left);
        r1.right = dfs(r1.right, r2.right);
        return r1;
    }

    /**
     * 965. 单值二叉树
     * 如果二叉树每个节点都具有相同的值，那么该二叉树就是单值二叉树。
     * 只有给定的树是单值二叉树时，才返回 true；否则返回 false。
     * 示例 1：
     * 输入：[1,1,1,1,1,null,1]
     * 输出：true
     *
     * @param root
     * @return
     */
    public boolean isUnivalTree(TreeNode root) {
        if (root == null) {
            return true;
        }
        int val = root.val;
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.val != val) {
                return false;
            }
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        return true;
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
     * 输出：
     * 4
     * /   \
     * 7     2
     * / \   / \
     * 9   6 3   1
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode temp = root.left;
            node.left = node.right;
            node.right = temp;
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        return root;
    }

    /**
     * 101. 对称二叉树
     * 给定一个二叉树，检查它是否是镜像对称的。
     * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
     * 1
     * / \
     * 2   2
     * / \ / \
     * 3  4 4  3
     * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
     * 1
     * / \
     * 2   2
     * \   \
     * 3    3
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetricDFS(root.left, root.right);
    }

    public boolean isSymmetricDFS(TreeNode r1, TreeNode r2) {
        if (r1 == null || r2 == null) {
            return false;
        }
        if (r1.val != r2.val) {
            return false;
        }
        return isSymmetricDFS(r1.left, r2.right) && isSymmetricDFS(r1.right, r2.left);
    }
}
