package com.zxw.leetcode.type.tree;

import com.zxw.common.datastruct.Node;
import com.zxw.common.datastruct.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树刷题总结：
 * 1.前序遍历
 * 2.中序遍历
 * 3.后序遍历
 * 判断每个节点下一步要做的动作是什么
 *
 * @author zxw
 * @date 2021/4/2 14:02
 */
public class TreeTest {
    static TreeNode show;

    public static void main(String[] args) {
        TreeTest treeTest = new TreeTest();
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
        boolean balanced = treeTest.isBalanced(treeNode);
        System.out.println(balanced);
        TreeNode tree = TreeOperation.createTree(new Integer[]{1, 2, 2, 3, null, null, 3, 4, null, null, 4});
        show = tree;
        treeTest.flatten(tree);
        treeTest.isBalanced(tree);
        treeTest.binaryTreePaths(treeNode);
    }

    /**
     * [257]二叉树的所有路径
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        binaryTreePathBFS(root, "", res);
        return res;
    }

    private void binaryTreePathBFS(TreeNode root, String path, List<String> res) {
        //如果为空，直接返回
        if (root == null)
            return;
        //如果是叶子节点，说明找到了一条路径，把它加入到res中
        if (root.left == null && root.right == null) {
            res.add(path + root.val);
            return;
        }
        //如果不是叶子节点，在分别遍历他的左右子节点
        binaryTreePathBFS(root.left, path + root.val + "->", res);
        binaryTreePathBFS(root.right, path + root.val + "->", res);
    }

    /**
     * [235]二叉搜索树的最近公共祖先
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return null;
    }

    /**
     * [112]路径总和
     *
     * @param root
     * @param targetSum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);

    }

    /**
     * [111]二叉树的最小深度
     *
     * @param root
     * @return
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int m1 = minDepth(root.left);
        int m2 = minDepth(root.right);
        return root.left == null || root.right == null ? m1 + m2 + 1 : Math.min(m1, m2) + 1;
    }

    /**
     * [110]平衡二叉树
     *
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isBalancedBFS(root) != -1;
    }

    public int isBalancedBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int var1 = isBalancedBFS(root.left);
        if (var1 == -1) {
            return -1;
        }
        int var2 = isBalancedBFS(root.right);
        if (var2 == -1) {
            return -1;
        }
        return Math.abs(var1 - var2) < 2 ? Math.max(var1, var2) + 1 : -1;
    }

    /**
     * [108]将有序数组转换为二叉搜索树
     *
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0) {
            return null;
        }
        TreeNode treeNode = new TreeNode(nums[0]);
        TreeNode cp = treeNode;
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
        }
        return treeNode;
    }

    /**
     * 104.二叉树的最大深度
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }


    /**
     * 101.对称二叉树
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetricBSF(root.left, root.right);
    }

    /**
     * 101.对称二叉树
     *
     * @return
     */
    public boolean isSymmetricBSF(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        if (t1.val != t2.val) {
            return false;
        }
        return isSymmetricBSF(t1.left, t2.right) && isSymmetricBSF(t1.right, t2.left);
    }

    /**
     * 100.相同的树
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
     * 二叉树展开为链表
     *
     * @param root
     */
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        flatten(root.left);
        flatten(root.right);
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.right = left;
        root.left = null;
        TreeNode temp = root;
        while (temp.right != null) {
            temp = temp.right;
        }
        temp.right = right;
    }

    /**
     * 填充每个结点的下一个右侧节点指针
     *
     * @param root
     * @return
     */
    public Node connect(Node root) {
        if (root == null) {
            return null;
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
        connectBFS(n2.left, n2.right);
        connectBFS(n1.right, n2.left);
    }

    /**
     * 226.翻转二叉树
     */
    public TreeNode invertTree(TreeNode root) {
        TreeNode node = root;
        invertTreeBFS(node);
        return root;
    }

    public void invertTreeBFS(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode temp = root.right;
        root.right = root.left;
        root.left = temp;
        invertTreeBFS(root.left);
        invertTreeBFS(root.right);
    }

}
