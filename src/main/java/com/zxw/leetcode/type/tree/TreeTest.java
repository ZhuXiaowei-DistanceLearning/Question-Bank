package com.zxw.leetcode.type.tree;

import com.zxw.common.datastruct.Node;
import com.zxw.common.datastruct.TreeNode;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayDeque;

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
        TreeNode tree = treeTest.createTree(new Integer[]{1, 2, 5, 3, 4, null, 6});
        show = tree;
        treeTest.flatten(tree);
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
        if(t1.val != t2.val){
            return false;
        }
        return isSymmetricBSF(t1.left, t2.right) && isSymmetricBSF(t1.right, t2.left);
    }

    /**
     * 相同的树
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

    public TreeNode createTree(Integer[] arr) {
        // 使用队列来存储每一层的非空节点，下一层的数目要比上一层高
        ArrayDeque<TreeNode> pre = new ArrayDeque<>();
        TreeNode root = new TreeNode(arr[0]);
        pre.addLast(root);
        // 表示要遍历的下一个节点
        int index = 0;
        while (!pre.isEmpty()) {

            ArrayDeque<TreeNode> cur = new ArrayDeque<>();
            while (!pre.isEmpty()) {
                TreeNode node = pre.removeFirst();
                TreeNode left = null;
                TreeNode right = null;
                // 如果对应索引上的数组不为空的话就创建一个节点,进行判断的时候，
                // 要先索引看是否已经超过数组的长度，如果索引已经超过了数组的长度，那么剩下节点的左右子节点就都是空了
                // 这里index每次都会增加，实际上是不必要的，但是这样写比较简单
                if (++index < arr.length && arr[index] != null) {
                    left = new TreeNode(arr[index]);
                    cur.addLast(left);
                }
                if (++index < arr.length && arr[index] != null) {
                    right = new TreeNode(arr[index]);
                    cur.addLast(right);
                }
                node.left = left;
                node.right = right;
            }
            pre = cur;
        }


        return root;
    }
}
