package com.zxw.leetcode.type.tree;

import com.zxw.datastruct.Node;
import com.zxw.datastruct.TreeNode;
import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
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
    TreeNode nodeRes;
    int max = 0;
    int sum = 0;
    int min = 99999;
    StringBuilder res = new StringBuilder();

    public static void main(String[] args) {
        TreeNode tree = TreeOperation.createTree(new Integer[]{3,9,20,null,null,15,7});
        TreeTest treeTest = new TreeTest();
        treeTest.minDepth(tree);
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
        tree = TreeOperation.createTree(new Integer[]{4, -7, -3, null, null, -9, -3, 9, -7, -4, null, 6, null, -6, -6, null, null, 0, 6, 5, null, 9, null, null, -1, -4, null, null, null, -2});
        show = tree;
        TreeOperation.show(treeNode);
        treeTest.flatten(tree);
        treeTest.isBalanced(tree);
        treeTest.diameterOfBinaryTree(tree);
        int i = treeTest.sumOfLeftLeaves(treeNode);
    }

    /**
     * 17.12 BiNode
     * @param root
     * @return
     */
    public TreeNode convertBiNode(TreeNode root) {
        convertBiNodeBFS(root);
        return nodeRes;
    }

    public void convertBiNodeBFS(TreeNode root) {
        if (root == null) {
            return;
        }
        convertBiNodeBFS(root.left);
        if (nodeRes == null) {
            nodeRes = new TreeNode(root.val);
            prevNode = nodeRes;
        } else {
            prevNode.right = new TreeNode(root.val);
            prevNode = prevNode.right;
        }
        convertBiNodeBFS(root.right);
    }

    /**
     * public boolean isSymmetric(TreeNode root) {
     * <p>
     * }
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetricBFS(root.left, root.right);
    }

    public boolean isSymmetricBFS(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        if (left.val != right.val) {
            return false;
        }
        isSymmetricBFS(left.left, left.right);
        isSymmetricBFS(right.left, right.right);
        isSymmetricBFS(left.right, right.left);
        return true;
    }

    /**
     * [1022]从根到叶的二进制数之和
     *
     * @param root
     * @return
     */
    public int sumRootToLeaf(TreeNode root) {
        if (root == null) {
            return 0;
        }
        sumRootToLeafBFS(root, "");
        return sum;
    }

    public void sumRootToLeafBFS(TreeNode root, String s) {
        if (root == null) {
            return;
        }
        s += root.val;
        if (root.left == null && root.right == null) {
            sum += Integer.parseInt(s, 2);
            return;
        }
        sumRootToLeafBFS(root.left, s);
        sumRootToLeafBFS(root.right, s);
    }

    /**
     * [993]二叉树的堂兄弟节点
     *
     * @param root
     * @param x
     * @param y
     * @return
     */
    public boolean isCousins(TreeNode root, int x, int y) {
        Map<String, Pair> map = new HashMap<>();
        isCousinsBFS(root, x, root, map, 0);
        isCousinsBFS(root, y, root, map, 0);
        Pair xp = map.get(x);
        Pair yp = map.get(y);
        if (xp.getKey().equals(yp.getKey()) && !xp.getValue().equals(yp.getValue())) {
            return true;
        }
        return false;
    }

    public void isCousinsBFS(TreeNode root, int value, TreeNode prev, Map map, int h) {
        if (root == null) {
            return;
        }
        h++;
        isCousinsBFS(root.left, value, root, map, h);
        isCousinsBFS(root.right, value, root, map, h);
        if (root.val == value) {
            if (prev == null) {
                map.put(value, new Pair(0, prev.val));
            } else {
                map.put(value, new Pair(h, prev.val));
            }
        }
    }

    /**
     * [938]二叉搜索树的范围和
     *
     * @param root
     * @param low
     * @param high
     * @return
     */
    public int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null) {
            return 0;
        }
        if (root.val > high) {
            rangeSumBST(root.left, low, high);
        } else if (root.val < low) {
            rangeSumBST(root.right, low, high);
        } else {
            max += root.val;
            rangeSumBST(root.left, low, high);
            rangeSumBST(root.right, low, high);
        }
        return max;
    }

    /**
     * [897]递增顺序查找树
     *
     * @param root
     * @return
     */
    public TreeNode increasingBST(TreeNode root) {
        TreeNode res = null;
        TreeNode prev = null;
        List<Integer> treeList = new ArrayList<>();
        for (int i = 0; i < treeList.size(); i++) {
            if (res == null) {
                res = new TreeNode(treeList.get(i));
                prev = res;
            } else {
                TreeNode next = new TreeNode(treeList.get(i));
                prev.right = next;
                prev = prev.right;
            }
        }
        return res;
    }

    public void increasingBSTBSF(TreeNode root, List<Integer> treeList) {
        if (root == null) {
            return;
        }
        increasingBSTBSF(root.left, treeList);
        increasingBSTBSF(root.right, treeList);
        treeList.add(root.val);
    }


    /**
     * [872]叶子相似的树
     */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        leafSimilarBFS(root1, l1);
        leafSimilarBFS(root2, l2);
        if (l1.size() != l2.size()) {
            return false;
        }
        for (int i = 0; i < l1.size(); i++) {
            if (!l1.get(i).equals(l2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public void leafSimilarBFS(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        leafSimilarBFS(root.left, list);
        leafSimilarBFS(root.right, list);
        if (root.left == null && root.right == null) {
            list.add(root.val);
        }
    }

    /**
     * [783]二叉搜索树节点最小距离
     *
     * @param root
     * @return
     */
    public int minDiffInBST(TreeNode root) {
        this.minDiffInBSTBFS(root);
        return min;
    }

    public void minDiffInBSTBFS(TreeNode root) {
        if (root == null) {
            return;
        }
        minDiffInBSTBFS(root.left);
        if (prevNode == null) {
            prevNode = root;
        } else {
            min = Math.min(Math.abs(prevNode.val - root.val), min);
        }
        minDiffInBSTBFS(root.right);
    }

    /**
     * [700]二叉搜索树中的搜索
     *
     * @param root
     * @param val
     * @return
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        TreeNode res = null;
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            if (poll == null) {
                continue;
            }
            if (poll.val == val) {
                res = poll;
            }
            if (poll.val > val) {
                queue.add(poll.left);
            } else {
                queue.add(poll.right);
            }
        }
        return res;
    }

    public TreeNode searchBSTBSF(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        }
        if (root.val > val) {
            return searchBSTBSF(root.left, val);
        } else {
            return searchBSTBSF(root.right, val);
        }
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
        if (root == null || (root.left == null && root.right == null)) {
            return -1;
        }

        int left = root.left.val;
        int right = root.right.val;

        if (left == root.val) {
            left = findSecondMinimumValue(root.left);
        }

        if (right == root.val) {
            right = findSecondMinimumValue(root.right);
        }

        if (left != -1 && right != -1) {
            return Math.min(left, right);
        }
        if (left != -1) {
            return left;
        }
        return right;
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

    public int minDepth2(TreeNode root) {
        if(root == null){
            return 0;
        }
        int res = 1;
        Queue<TreeNode> queue = new LinkedBlockingQueue();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode t = queue.poll();
                if (t.left == null && t.right == null) {
                    return res;
                }
                if (t.left != null) queue.add(t.left);
                if (t.right != null) queue.add(t.right);
            }
            res++;
        }
        return res;
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
    public boolean isSymmetric2(TreeNode root) {
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
