package com.zxw.leetcode.type.tree;

import com.zxw.common.datastruct.Node;
import com.zxw.common.datastruct.TreeNode;
import jnr.ffi.annotations.In;
import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.formula.functions.T;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 二叉树刷题总结：
 * 1.前序遍历
 * 2.中序遍历
 * 3.后序遍历
 * 判断每个节点下一步要做的动作是什么
 * 递归类型的题目，看看当前节点以及子节点需要做什么
 *
 * @author zxw
 * @date 2021/4/2 14:02
 */
public class TreeTest {
    static TreeNode show;
    TreeNode prevNode;
    int max = 0;
    int min = 99999;
    StringBuilder res = new StringBuilder();

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
        TreeNode tree = TreeOperation.createTree(new Integer[]{4, -7, -3, null, null, -9, -3, 9, -7, -4, null, 6, null, -6, -6, null, null, 0, 6, 5, null, 9, null, null, -1, -4, null, null, null, -2});
        show = tree;
        TreeOperation.show(treeNode);
        treeTest.flatten(tree);
        treeTest.isBalanced(tree);
        treeTest.diameterOfBinaryTree(tree);
        int i = treeTest.sumOfLeftLeaves(treeNode);
    }

    /**
     * [559]N 叉树的最大深度
     *
     * @param root
     * @return
     */
    public int maxDepth(Node root) {
        Queue<Pair<Node, Integer>> stack = new LinkedList<>();
        if (root != null) {
            stack.add(new Pair(root, 1));
        }

        int depth = 0;
        while (!stack.isEmpty()) {
            Pair<Node, Integer> current = stack.poll();
            root = current.getKey();
            int current_depth = current.getValue();
            if (root != null) {
                depth = Math.max(depth, current_depth);
//                for (Node c : root.children) {
//                    stack.add(new Pair(c, current_depth + 1));
//                }
            }
        }
        return depth;
    }

    /**
     * [671]二叉树中第二小的节点
     *
     * @param root
     * @return
     */
    public int findSecondMinimumValue(TreeNode root) {

        return 0;
    }

    /**
     * 两数之和 IV - 输入 BST
     *
     * @param root
     * @param k
     * @return
     */
    public boolean findTarget(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        findTargetBFS(root, list);
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; i < list.size(); j++) {
                if (list.get(i) + list.get(j) == k) {
                    return true;
                }
            }
        }
        return false;
    }

    public void findTargetBFS(TreeNode root, List list) {
        if (root == null) {
            return;
        }
        findTargetBFS(root.left, list);
        list.add(root.val);
        findTargetBFS(root.right, list);
    }

    /**
     * [637]二叉树的层平均值
     *
     * @param root
     * @return
     */
    List<Double> list = new LinkedList<>();

    public List<Double> averageOfLevels(TreeNode root) {
        bfs(root);
        return list;
    }

    public void bfs(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            double sum = 0;
            int m = queue.size();
            for (int i = 0; i < m; i++) {
                TreeNode node = queue.poll();
                sum += (double) node.val;
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            list.add(sum / m);
        }
    }

    /**
     * [606]根据二叉树创建字符串
     *
     * @param t
     * @return
     */
    public String tree2str(TreeNode t) {
        tree2strBFS(t);
        return res.toString();
    }

    public void tree2strBFS(TreeNode t) {
        if (t == null) return;
        res.append(t.val);
        if (t.left != null || t.right != null) // 当其左孩子或右孩子不为空时，不管当前的根节点是否为空都要输出左孩子的值
        {
            res.append("(");
            tree2strBFS(t.left);
            res.append(")");
            if (t.right != null) // 只有当右孩子不为空时，才用输出右孩子的值
            {
                res.append("(");
                tree2strBFS(t.right);
                res.append(")");
            }
        }
    }

    /**
     * [563]二叉树的坡度
     *
     * @param root
     * @return
     */
    int tilt = 0;   // 结果

    public int findTilt(TreeNode root) {
        findTiltBFS(root);
        return tilt;
    }

    public int findTiltBFS(TreeNode root) {
        if (root == null) { // 递归出口，空树的节点之和为0
            return 0;
        }
        int leftTreeSum = findTiltBFS(root.left);    // 计算当前节点的左子树节点之和
        int rightTreeSum = findTiltBFS(root.right);  // 计算当前节点的右子树节点之和
        tilt += Math.abs(leftTreeSum - rightTreeSum);   // 计算当前节点的坡度并加入结果
        return root.val + leftTreeSum + rightTreeSum;   // 返回以当前节点为根的整棵树的节点之和
    }

    /**
     * [543]二叉树的直径
     *
     * @param root
     * @return
     */
    public int diameterOfBinaryTree(TreeNode root) {
        diameterOfBinaryTreeBFS(root);
        return max;
    }

    public int diameterOfBinaryTreeBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = diameterOfBinaryTreeBFS(root.left);
        int rightDepth = diameterOfBinaryTreeBFS(root.right);
        max = Math.max(max, leftDepth + rightDepth);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * [530]二叉搜索树的最小绝对差
     *
     * @param root
     * @return
     */
    public int getMinimumDifference(TreeNode root) {
//        List<Integer> list = new ArrayList<>();
//        int min = -1;
//        this.getMinimumDifferenceBFS(root,list);
//        for (int i = 0; i < list.size(); i++) {
//            for (int j = i + 1; j < list.size(); j++) {
//                int res = Math.abs(list.get(i) - list.get(j));
//                min = (min == -1) ? res : Math.min(min, res);
//            }
//        }
//        return min;
        AtomicInteger ac = new AtomicInteger(9999);
        this.getMinimumDifferenceBFS2(root, ac);
        return ac.get();
    }

    public void getMinimumDifferenceBFS(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        list.add(root.val);
        getMinimumDifferenceBFS(root.left, list);
        getMinimumDifferenceBFS(root.right, list);
    }

    /**
     * 中序遍历版本
     *
     * @param root
     */
    public void getMinimumDifferenceBFS2(TreeNode root, AtomicInteger min) {
        if (root == null) {
            return;
        }
        getMinimumDifferenceBFS2(root.left, min);
        if (prevNode != null) {
            min.set(Math.min(Math.abs(root.val - prevNode.val), min.get()));
        }
        prevNode = root;
        getMinimumDifferenceBFS2(root.right, min);
    }

    /**
     * [501]二叉搜索树中的众数
     *
     * @param root
     * @return
     */
    public int[] findMode(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        findModeBFS(root, map);
        AtomicInteger ac = new AtomicInteger(-1);
        map.forEach((k, v) -> {
            if (v > ac.get()) {
                list.removeAll(list);
                list.add(k);
                ac.set(v);
            } else if (v == ac.get()) {
                list.add(k);
            }
        });
        return list.stream().mapToInt(Integer::valueOf).toArray();
    }

    public void findModeBFS(TreeNode root, Map<Integer, Integer> map) {
        if (root == null) {
            return;
        }
        Integer orDefault = map.getOrDefault(root.val, 0);
        map.put(root.val, orDefault + 1);
        findModeBFS(root.left, map);
        findModeBFS(root.right, map);
    }

    /**
     * [404]左叶子之和
     *
     * @param root
     * @return
     */
    public int sumOfLeftLeaves(TreeNode root) {
        return helper(root);
    }

    public int helper(TreeNode node) {
        int sum = 0;
        if (node == null) {
            return 0;
        }
        if (node.left != null && (node.left.left == null && node.left.right == null)) {
            sum += node.left.val;
        }
        sum += helper(node.left) + helper(node.right);
        return sum;
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
