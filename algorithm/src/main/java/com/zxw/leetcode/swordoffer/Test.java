package com.zxw.leetcode.swordoffer;

import com.zxw.datastruct.ListNode;
import com.zxw.datastruct.TreeNode;
import com.zxw.leetcode.swordoffer.data.MaxQueue;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zxw
 * @date 2020-05-11 08:8:46
 * @Description 牛客练习
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(2 / 10);
        TreeNode treeNode = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode5 = new TreeNode(5);
        MaxQueue mq = new MaxQueue();
        treeNode.left = treeNode2;
        treeNode.right = treeNode3;
        treeNode2.left = treeNode4;
        treeNode3.right = treeNode5;
        Test t = new Test();
        t.constructArr(new int[]{1, 2, 3, 4, 5});
        t.isStraight(new int[]{1, 2, 3, 4, 5});
        t.maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
        t.findContinuousSequence(9);
        t.twoSum(new int[]{2, 7, 11, 15}, 9);
        t.twoSum(new int[]{14, 15, 16, 22, 53, 60}, 76);
        t.maxValue(new int[][]{{1, 2, 5}, {3, 2, 1}});
        t.levelOrder2(treeNode);
        t.permutation("abc");
//        t.countDigitOne(824883294);
        t.lengthOfLongestSubstring("pwwkew");
    }

    /**
     * 面试题03. 数组中重复的数字
     * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
     * 示例 1：
     * 输入：
     * new int[]{2, 3, 1, 0, 2, 5, 3}
     * 输出：2 或 3
     *
     * @param nums
     * @return
     */
    public int findRepeatNumber(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            //开始的
            while (nums[i] != i) {
                if (nums[nums[i]] == nums[i]) {
                    return nums[i];
                }
                int temp = nums[i];
                nums[i] = nums[temp];
                nums[temp] = temp;
            }
        }
        return -1;
    }


    /**
     * 面试题04. 二维数组中的查找
     * 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        // method 1: 暴力解法
        // 执行用时 :1 ms在所有 Java 提交中击败了31.41%的用户
        // 内存消耗 :45.5 MB, 在所有 Java 提交中击败了100.00%的用户
        boolean b = false;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (target == matrix[i][j]) {
                    return true;
                }
            }
        }
        // method 2:线性解法
        // 执行用时 :0 ms在所有 Java 提交中击败了100%的用户
        // 内存消耗 :45.2 MB, 在所有 Java 提交中击败了100.00%的用户
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (target < matrix[i][j]) {
                    break;
                }
                if (target == matrix[i][j]) {
                    return true;
                }
            }
        }
        return b;
    }

    /**
     * 面试题05. 替换空格
     *
     * @param s
     * @return
     */
    public String replaceSpace(String s) {
        return s.replace(" ", "%");
    }

    /**
     * 面试题06. 从尾到头打印链表
     * 输入：head = [1,3,2]
     * 输出：[2,3,1]
     *
     * @param head
     * @return
     */
    public int[] reversePrint(ListNode head) {
        Stack<Integer> stack = new Stack<>();
        while (head != null) {
            int val = head.val;
            stack.push(val);
            head = head.next;
        }
        int[] arr = new int[stack.size()];
        for (int i = 0; !stack.empty(); i++) {
            arr[i] = stack.pop();
        }
        return arr;
    }

    /**
     * 面试题07. 重建二叉树
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return null;
    }

    /**
     * 面试题10- I. 斐波那契数列
     * F(0) = 0,   F(1) = 1
     * F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
     *
     * @param n
     * @return
     */
    public int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n <= 1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % 1000000007;
        }
        return dp[n];
    }

    /**
     * 面试题10- II. 青蛙跳台阶问题
     * 示例 1：
     * 输入：n = 2
     * 输出：2
     *
     * @param n
     * @return
     */
    public int numWays(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = (dp[1] + dp[2]) % 1000000007;
        }
        return dp[n];
    }

    /**
     * 面试题11. 旋转数组的最小数字
     * 示例 1：
     * 输入：[3,4,5,1,2]
     * 输出：1
     *
     * @param numbers
     * @return
     */
    public int minArray(int[] numbers) {
        if (numbers.length == 0) {
            return 0;
        }
        int res = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < res) {
                res = numbers[i];
            }
        }
        return res;
    }


    /**
     * 面试题12. 矩阵中的路径
     * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一格开始，每一步可以在矩阵中向左、右、上、下移动一格。如果一条路径经过了矩阵的某一格，那么该路径不能再次进入该格子。例如，在下面的3×4的矩阵中包含一条字符串“bfce”的路径（路径中的字母用加粗标出）。
     * [["a","b","c","e"],
     * ["s","f","c","s"],
     * ["a","d","e","e"]]
     * 但矩阵中不包含字符串“abfb”的路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入这个格子。
     * 示例 1：
     * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
     * 输出：true
     * 示例 2：
     * 输入：board = [["a","b"],["c","d"]], word = "abcd"
     * 输出：false
     * 提示：
     * 1 <= board.length <= 200
     * 1 <= board[i].length <= 200
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        return false;
    }


    /**
     * 面试题13. 机器人的运动范围
     * 地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。一个机器人从坐标 [0, 0] 的格子开始移动，它每次可以向左、右、上、下移动一格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？
     * 示例 1：
     * 输入：m = 2, n = 3, k = 1
     * 输出：3
     * 示例 2：
     * 输入：m = 3, n = 1, k = 0
     * 输出：1
     *
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int movingCount(int m, int n, int k) {
        int[][] dp = new int[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                int sum = sum(i, j);
                if (sum <= k) {
                    res = dp[i][j] + 1;
                }
            }
        }
        int mid = m > n ? m : n;
        return res + 1 > mid ? res + 1 : mid;
    }

    /**
     * 面试题15. 二进制中1的个数
     * 请实现一个函数，输入一个整数，输出该数二进制表示中 1 的个数。例如，把 9 表示成二进制是 1001，有 2 位是 1。因此，如果输入 9，则该函数输出 2。
     * 示例 1：
     * 输入：00000000000000000000000000001011
     * 输出：3
     * 解释：输入的二进制串 00000000000000000000000000001011 中，共有三位为 '1'。
     *
     * @param n
     * @return
     */
    public int hammingWeight(int n) {
        int res = 0;
        while (n != 0) {
            n = n & (n - 1);
            res++;
        }
        return res;
    }

    /**
     * 面试题16. 数值的整数次方
     * 示例 1:
     * 输入: 2.00000, 10
     * 输出: 1024.00000
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return x;
        }
        if (n == -1) {
            return 1 / x;
        }
        if (n % 2 == 0) {
            double t = myPow(x, n / 2);
            return t * t;
        } else {
            double t = myPow(x, n / 2);
            if (n < 0) {
                x = (1 / x);
            }
            return t * t * x;
        }
    }

    /**
     * 面试题17. 打印从1到最大的n位数
     * 输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。
     * 示例 1:
     * <p>
     * 输入: n = 1
     * 输出: [1,2,3,4,5,6,7,8,9]
     *
     * @param n
     * @return
     */
    public int[] printNumbers(int n) {
        int len = 1 << n;
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = i + 1;
        }
        return arr;
    }

    private int sum(int m, int n) {
        int sum = 0;
        while (m > 10) {
            sum += m % 10;
            m = m % 10;
        }
        sum += m;
        while (n > 10) {
            sum += n % 10;
            n = n % 10;
        }
        sum += n;
        return sum;
    }

    /**
     * 面试题18. 删除链表的节点
     * 给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。
     * 返回删除后的链表的头节点。
     * 注意：此题对比原题有改动
     * 示例 1:
     * 输入: head = [4,5,1,9], val = 5
     * 输出: [4,1,9]
     * 解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
     * 示例 2:
     * 输入: head = [4,5,1,9], val = 1
     * 输出: [4,5,9]
     * 解释: 给定你链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.
     *
     * @param head
     * @param val
     * @return
     */
    public ListNode deleteNode(ListNode head, int val) {
        ListNode node = head;
        ListNode node1 = node;
        int bitCount = 0;
        while (head != null) {
            int val1 = head.val;
            node1 = head;
            head = head.next;
            if (val1 == val) {
                if (head == null) {
                    node1.next = null;
                } else if (bitCount == 0) {
                    node1 = node1.next;
                } else {
                    node1.next = head.next;
                }
                break;
            }
        }
        return node;
    }

    /**
     * 面试题20. 表示数值的字符串
     * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100"、"5e2"、"-123"、"3.1416"、"0123"都表示数值，但"12e"、"1a3.14"、"1.2.3"、"+-5"、"-1E-16"及"12e+5.4"都不是。
     *
     * @param s
     * @return
     */
    public boolean isNumber(String s) {
        Stack stack = new Stack();
        for (int i = 0; i < s.length(); i++) {

        }
        return false;
    }

    /**
     * 面试题21. 调整数组顺序使奇数位于偶数前面
     * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。
     * 示例：
     * 输入：nums = [1,2,3,4]
     * 输出：[1,3,2,4]
     * 注：[3,1,2,4] 也是正确的答案之一。
     *
     * @param nums
     * @return
     */
    public int[] exchange(int[] nums) {
        /**
         *  List<Integer> ou = new ArrayList();
         *         List<Integer> qi = new ArrayList();
         *         int[] arr = new int[nums.length];
         *         for (int i = 0; i < nums.length; i++) {
         *             if ((nums[i] & 1) == 1) {
         *                 qi.add(nums[i]);
         *             } else {
         *                 ou.add(nums[i]);
         *             }
         *         }
         *         for (int i = 0; i < qi.size(); i++) {
         *             arr[i] = qi.get(i);
         *         }
         *         for (int i = 0; i < ou.size(); i++) {
         *             arr[qi.size() + i] = ou.get(i);
         *         }
         *          return arr;
         */
        int l = 0, r = nums.length - 1;
        while (l < r) {
            // 是否为偶数
            if ((nums[l] & 1) == 0) {
                // 是否为奇数
                if ((nums[r] & 1) == 1) {
                    int temp = nums[l];
                    nums[l] = nums[r];
                    nums[r] = temp;
                } else {
                    r--;
                }
            } else {
                l++;
            }
        }
        return nums;
    }

    /**
     * 面试题22. 链表中倒数第k个节点
     * 输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。例如，一个链表有6个节点，从头节点开始，它们的值依次是1、2、3、4、5、6。这个链表的倒数第3个节点是值为4的节点。
     * 示例：
     * 给定一个链表: 1->2->3->4->5, 和 k = 2.
     * 返回链表 4->5.
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode node = head;
        while (head != null) {
            if (k > 0) {
                k--;
            } else {
                node = node.next;
            }
            head = head.next;
        }
        return node.next;
    }

    /**
     * 面试题24. 反转链表
     * 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
     * 示例:
     * <p>
     * 输入: 1->2->3->4->5->NULL
     * 输出: 5->4->3->2->1->NULL
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode node = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = node;
            node = head;
            head = next;
        }
        return node;
    }

    /**
     * 面试题25. 合并两个排序的链表
     * 输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的。
     * <p>
     * 示例1：
     * <p>
     * 输入：1->2->4, 1->3->4
     * 输出：1->1->2->3->4->4
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode node = new ListNode(0);
        ListNode tail = node;
        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                node.next = l2;
            } else {
                node.next = l1;
            }
            l1 = l1.next;
            l2 = l2.next;
            node = node.next;
        }
        if (l1 != null) {
            node = l1;
        }
        if (l2 != null) {
            node = l2;
        }
        return tail.next;
    }

    /**
     * 面试题26. 树的子结构
     * 输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
     * <p>
     * B是A的子结构， 即 A中有出现和B相同的结构和节点值。
     * <p>
     * 例如:
     * 给定的树 A:
     * <p>
     *      3
     *     / \
     *    4   5
     *   / \
     *  1   2
     * 给定的树 B：
     * <p>
     *    4 
     *   /
     *  1
     * 返回 true，因为 B 与 A 的一个子树拥有相同的结构和节点值。
     * <p>
     * 示例 1：
     * <p>
     * 输入：A = [1,2,3], B = [3,1]
     * 输出：false
     * 示例 2：
     * <p>
     * 输入：A = [3,4,5,1,2], B = [4,1]
     * 输出：true
     *
     * @param A
     * @param B
     * @return
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        return true;
    }

    /**
     * 面试题27. 二叉树的镜像
     *
     * @param root
     * @return
     */
    public TreeNode mirrorTree(TreeNode root) {
        if (root != null) {
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
            mirrorTree(root.left);
            mirrorTree(root.right);
        }
        return root;
    }

    /**
     * 面试题28. 对称的二叉树
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        return false;
    }

    /**
     * 面试题29. 顺时针打印矩阵
     *
     * @param matrix
     * @return
     */
    public int[] spiralOrder(int[][] matrix) {
        if (matrix.length == 0) return new int[0];
        int l = 0, r = matrix[0].length - 1, t = 0, b = matrix.length - 1, x = 0;
        int[] res = new int[(r + 1) * (b + 1)];
        while (true) {
            for (int i = l; i <= r; i++) res[x++] = matrix[t][i]; // left to right.
            if (++t > b) break;
            for (int i = t; i <= b; i++) res[x++] = matrix[i][r]; // top to bottom.
            if (l > --r) break;
            for (int i = r; i >= l; i--) res[x++] = matrix[b][i]; // right to left.
            if (t > --b) break;
            for (int i = b; i >= t; i--) res[x++] = matrix[i][l]; // bottom to top.
            if (++l > r) break;
        }
        return res;
    }

    /**
     * 面试题32 - I. 从上到下打印二叉树
     *
     * @param root
     * @return
     */
    public int[] levelOrder(TreeNode root) {
        if (root == null) {
            return new int[]{};
        }
        List<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedBlockingQueue();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            list.add(node.val);
            if (node.left != null) {

                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    /**
     * 面试题32 - II. 从上到下打印二叉树 II
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        int count = 1;
        while (!queue.isEmpty()) {
            LinkedList<Integer> res = new LinkedList<>();
            count++;
            for (int i = queue.size(); i > 0; i--) {
                TreeNode poll = queue.poll();
                if ((count & 1) == 0) {
                    res.addFirst(poll.val);
                } else {
                    res.addFirst(poll.val);
                }
                if (poll.left != null) queue.add(poll.left);
                if (poll.right != null) queue.add(poll.right);
            }
            list.add(res);
        }
        return list;
    }

    /**
     * 面试题33. 二叉搜索树的后序遍历序列
     *
     * @param postorder
     * @return
     */
    public boolean verifyPostorder(int[] postorder) {
        return false;
    }

    /**
     * 面试题34. 二叉树中和为某一值的路径
     * 输入一棵二叉树和一个整数，打印出二叉树中节点值的和为输入整数的所有路径。从树的根节点开始往下一直到叶节点所经过的节点形成一条路径。
     * <p>
     * 示例:
     * 给定如下二叉树，以及目标和 sum = 22，
     * <p>
     * 5
     * / \
     * 4   8
     * /   / \
     * 11  13  4
     * /  \    / \
     * 7    2  5   1
     * [
     * [5,4,11,2],
     * [5,8,4,5]
     * ]
     *
     * @param root
     * @param sum
     * @return
     */
    List<List<Integer>> list = new ArrayList<>();
    LinkedList<Integer> track = new LinkedList<>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        getSum(root, sum);
        return list;
    }

    public void getSum(TreeNode root, int sum) {
        if (root == null) {
            return;
        }
        int val = root.val;
        track.add(val);
        if ((sum -= val) == 0 && (root.left == null && root.right == null)) {
            list.add(new LinkedList<>(track));
            track.removeLast();
            return;
        }
        getSum(root.left, sum);
        getSum(root.right, sum);
        track.removeLast();
    }

    /**
     * 面试题38. 字符串的排列
     * 输入一个字符串，打印出该字符串中字符的所有排列。
     * 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。
     * 示例:
     * 输入：s = "abc"
     * 输出：["abc","acb","bac","bca","cab","cba"]
     *
     * @param s
     * @return
     */
    List<String> stringLinkedList = new ArrayList<>();

    public String[] permutation(String s) {
        StringBuilder sb = new StringBuilder();
        backtracePer(s, sb);
        String[] arr = new String[stringLinkedList.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = stringLinkedList.get(i);
        }
        return arr;
    }

    /**
     * 面试题39. 数组中出现次数超过一半的数字
     * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
     * 示例 1:
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     * 输入: [1, 2, 3, 2, 2, 2, 5, 4, 2]
     * 输出: 2
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        // 投票法
        int vote = nums[0];
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (vote == nums[i]) {
                count++;
            } else {
                count--;
                if (count <= 0) {
                    vote = nums[i];
                    count = 1;
                }
            }
        }
        return vote;
    }

    /**
     * 面试题40. 最小的k个数
     * 输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
     * 示例 1：
     * 输入：arr = [3,2,1], k = 2
     * 输出：[1,2] 或者 [2,1]
     * 示例 2：
     * 输入：arr = [0,1,2,1], k = 1
     * 输出：[0]
     *
     * @param arr
     * @param k
     * @return
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        int[] array = new int[k];
        Arrays.sort(arr);
        for (int i = 0; i < k; i++) {
            array[i] = arr[i];
        }
        return array;
    }

    public void backtracePer(String s, StringBuilder sb) {
        for (int i = 0; i < s.length(); i++) {
            String var1 = String.valueOf(s.charAt(i));
            if (sb.indexOf(var1) != -1) {
                continue;
            }
            sb.append(var1);
            if (sb.length() == s.length()) {
                stringLinkedList.add(sb.toString());
                sb.deleteCharAt(sb.length() - 1);
                continue;
            }
            backtracePer(s, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /**
     * 面试题42. 连续子数组的最大和
     * 输入一个整型数组，数组里有正数也有负数。数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
     * 要求时间复杂度为O(n)。
     * 示例1:
     * 输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int[] dp = new int[nums.length + 1];
        dp[0] = nums[0];
        int res = dp[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(nums[i] + dp[i - 1], nums[i]);
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /**
     * 面试题43. 1～n整数中1出现的次数
     * 输入一个整数 n ，求1～n这n个整数的十进制表示中1出现的次数。
     * <p>
     * 例如，输入12，1～12这些整数中包含1 的数字有1、10、11和12，1一共出现了5次。
     * 示例 1：
     * 输入：n = 12
     * 输出：5
     * 示例 2：
     * 输入：n = 13
     * 输出：6
     *
     * @param n
     * @return
     */
    public int countDigitOne(int n) {
        System.out.println(LocalTime.now());
        int count = 1;
        if (n < 10) {
            return 1;
        }
        for (int i = 10; i <= n; i++) {
            int temp = i;
            while (temp != 0) {
                if (temp % 10 == 1) {
                    count++;
                }
                temp = temp / 10;
            }
        }
        System.out.println(LocalTime.now());
        return count;
    }

    /**
     * 面试题44. 数字序列中某一位的数字
     * 数字以0123456789101112131415…的格式序列化到一个字符序列中。在这个序列中，第5位（从下标0开始计数）是5，第13位是1，第19位是4，等等。
     * <p>
     * 请写一个函数，求任意第n位对应的数字。
     * 示例 1：
     * 输入：n = 3
     * 输出：3
     * 示例 2：
     * 输入：n = 11
     * 输出：0
     *
     * @param n
     * @return
     */
    public int findNthDigit(int n) {
        return 0;
    }

    /**
     * 面试题45. 把数组排成最小的数
     * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
     * 输入: [10,2]
     * 示例 1:
     * 输出: "102"
     * 输入: [3,30,34,5,9]
     * 示例 2:
     * 输出: "3033459"
     *
     * @param nums
     * @return
     */
    public String minNumber(int[] nums) {
        Arrays.sort(nums);
        StringBuilder sb = new StringBuilder();
        for (int num : nums) {
            sb.append(num);
        }
        return sb.toString();
    }

    /**
     * 面试题46. 把数字翻译成字符串
     * 给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。一个数字可能有多个翻译。请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。
     * 示例 1:
     * 输入: 12258
     * 输出: 5
     * 解释: 12258有5种不同的翻译，分别是"bccfi", "bwfi", "bczi", "mcfi"和"mzi"
     *
     * @param num
     * @return
     */
    public int translateNum(int num) {
        int[] dp = new int[num];
        for (int i = 0; i < num; i++) {

        }
        return 0;
    }

    /**
     * 面试题47. 礼物的最大价值
     * 在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直到到达棋盘的右下角。给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？
     * 示例 1:
     * 输入:
     * [
     *   [1,3,1],
     *   [1,5,1],
     *   [4,2,1]
     * ]
     * 输出: 12
     * 解释: 路径 1→3→5→2→1 可以拿到最多价值的礼物
     *
     * @param grid
     * @return
     */
    public int maxValue(int[][] grid) {
        int l1 = grid.length;
        int l2 = grid[0].length;
        if (l1 == 1) {
            int sum = 0;
            for (int i = 0; i < l2; i++) {
                sum += grid[0][i];
            }
            return sum;
        }
        int[][] dp = new int[l1][l2];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < l1; i++) {
            dp[i][0] = grid[i][0] + dp[i - 1][0];
        }
        for (int i = 1; i < l2; i++) {
            dp[0][i] = grid[0][i] + dp[0][i - 1];
        }
        for (int i = 1; i < l1; i++) {
            for (int j = 1; j < l2; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[l1 - 1][l2 - 1];
    }

    /**
     * 面试题48. 最长不含重复字符的子字符串
     * 请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
     * 示例 1:
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        int l = 0, r = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            // 判断是否重复
            if (map.containsKey(s.charAt(i))) {
                // 移动左窗口
                l = Math.max(map.get(s.charAt(i)), l);
            }
            res = Math.max(r - l + 1, res);
            map.put(s.charAt(i), i + 1);
            r++;
        }
        return res;
    }

    /**
     * 面试题49. 丑数
     * 我们把只包含因子 2、3 和 5 的数称作丑数（Ugly Number）。求按从小到大的顺序的第 n 个丑数。
     * 示例:
     * 输入: n = 10
     * 输出: 12
     * 解释: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 是前 10 个丑数。
     *
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        return 0;
    }

    /**
     * 面试题50. 第一个只出现一次的字符
     * 在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。 s 只包含小写字母。
     * 示例:
     * s = "abaccdeff"
     * 返回 "b"
     * s = ""
     * 返回 " "
     *
     * @param s
     * @return
     */
    public char firstUniqChar(String s) {
        Map<Character, Boolean> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.containsKey(s.charAt(i)));
        }
        for (int i = 0; i < s.length(); i++) {
            if (!map.get(i)) {
                return s.charAt(i);
            }
        }
        return ' ';
    }

    /**
     * 面试题52. 两个链表的第一个公共节点
     * 输入两个链表，找出它们的第一个公共节点。
     * <p>
     * 如下面的两个链表：
     * <p>
     * <p>
     * <p>
     * 在节点 c1 开始相交。
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode l1 = headA;
        ListNode l2 = headB;
        int count = 0;
        while (l1 != l2) {
            l1 = l1.next;
            l2 = l2.next;
            if (l1 == null) {
                l1 = headB;
                count++;
            }
            if (l2 == null) {
                l2 = headA;
                count++;
            }
            if (count > 2) {
                return null;
            }
        }
        return l1;
    }

    /**
     * 面试题53 - I. 在排序数组中查找数字 I
     * 统计一个数字在排序数组中出现的次数。
     * 示例 1:
     * 输入: nums = [5,7,7,8,8,10], target = 8
     * 输出: 2
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int c = 0;
        for (int i = 0; i < nums.length; i++) {
            if (target == nums[i]) {
                c++;
            }
        }
        return c;
    }

    /**
     * 面试题53 - II. 0～n-1中缺失的数字
     *
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 面试题54. 二叉搜索树的第k大节点
     *
     * @param root
     * @param k
     * @return
     */
    public int kthLargest(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            list.add(node.val);
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        Arrays.sort(arr);
        return arr[k];
    }

    /**
     * 面试题55 - I. 二叉树的深度
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
     * 面试题55 - II. 平衡二叉树
     * 输入一棵二叉树的根节点，判断该树是不是平衡二叉树。如果某二叉树中任意节点的左右子树的深度相差不超过1，那么它就是一棵平衡二叉树。
     *
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        return isBbfs(root) != -1;
    }

    public int isBbfs(TreeNode root) {
        if (root == null) return 0;
        int left = isBbfs(root.left);
        if (left == -1) return -1;
        int right = isBbfs(root.right);
        if (right == -1) return -1;
        return Math.abs(left - right) < 2 ? Math.max(left, right) + 1 : -1;
    }

    /**
     * 面试题56 - I. 数组中数字出现的次数
     * 一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
     *
     * @param nums
     * @return
     */
    public int[] singleNumbers(int[] nums) {
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {

        }
        return res;
    }

    /**
     * 面试题57. 和为s的两个数字
     * 输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。如果有多对数字的和等于s，则输出任意一对即可。
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int i = nums[l] + nums[r];
            if (i == target) {
                res[0] = nums[l];
                res[1] = nums[r];
                return res;
            } else if (i > target) {
                r--;
            } else {
                l++;
            }
        }
        return res;
    }

    /**
     * 面试题57 - II. 和为s的连续正数序列
     * 输入一个正整数 target ，输出所有和为 target 的连续正整数序列（至少含有两个数）。
     * <p>
     * 序列内的数字由小到大排列，不同序列按照首个数字从小到大排列。
     * 示例 1：
     * <p>
     * 输入：target = 9
     * 输出：[[2,3,4],[4,5]]
     * 示例 2：
     * 输入：target = 15
     * 输出：[[1,2,3,4,5],[4,5,6],[7,8]]
     *
     * @param target
     * @return
     */
    public int[][] findContinuousSequence(int target) {
        List<int[]> res = new ArrayList<>();
        for (int i = 1; i < target; i++) {
            int ans = i;
            List<Integer> list = new ArrayList<>();
            list.add(i);
            for (int j = i + 1; j < target; j++) {
                ans += j;
                list.add(j);
                if (ans == target) {
                    res.add(list.stream().mapToInt(Integer::valueOf).toArray());
                    break;
                } else if (ans > target) {
                    break;
                }
            }
        }
        return res.toArray(new int[res.size()][]);
    }

    /**
     * 面试题58 - I. 翻转单词顺序
     * 输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。例如输入字符串"I am a student. "，则输出"student. a am I"。
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        String[] split = s.trim().split(" ");
        for (int i = split.length - 1; i >= 0; i--) {
            if (split[i].equals("")) {
                continue;
            }
            sb.append(split[i]);
            sb.append(" ");
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 面试题58 - II. 左旋转字符串
     * 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
     *
     * @param s
     * @param n
     * @return
     */
    public String reverseLeftWords(String s, int n) {
        return s.substring(n + 1, s.length()) + s.substring(0, n);
    }

    /**
     * 面试题59 - I. 滑动窗口的最大值
     * 给定一个数组 nums 和滑动窗口的大小 k，请找出所有滑动窗口里的最大值。
     * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
     * 输出: [3,3,5,5,6,7]
     * 解释:
     * <p>
     * 滑动窗口的位置                最大值
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       3
     * 1 [3  -1  -3] 5  3  6  7       3
     * 1  3 [-1  -3  5] 3  6  7       5
     * 1  3  -1 [-3  5  3] 6  7       5
     * 1  3  -1  -3 [5  3  6] 7       6
     * 1  3  -1  -3  5 [3  6  7]      7
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (k == 0) {
            return new int[0];
        }
        int l = 0, r = k - 1;
        int[] arr = new int[nums.length - k + 1];
        int c = 0;
        while (r < nums.length) {
            int max = nums[l];
            for (int i = l + 1; i <= r; i++) {
                if (nums[i] > max) {
                    max = nums[i];
                }
            }
            arr[c] = max;
            c++;
            l++;
            r++;
        }
        return arr;
    }

    /**
     * 面试题60. n个骰子的点数
     * 把n个骰子扔在地上，所有骰子朝上一面的点数之和为s。输入n，打印出s的所有可能的值出现的概率。
     * 你需要用一个浮点数数组返回答案，其中第 i 个元素代表这 n 个骰子所能掷出的点数集合中第 i 小的那个的概率。
     * 示例 1:
     * 输入: 1
     * 输出: [0.16667,0.16667,0.16667,0.16667,0.16667,0.16667]
     * 示例 2:
     * 输入: 2
     * 输出: [0.02778,0.05556,0.08333,0.11111,0.13889,0.16667,0.13889,0.11111,0.08333,0.05556,0.02778]
     *
     * @param n
     * @return
     */
    public double[] twoSum(int n) {
        return new double[0];
    }

    /**
     * 面试题61. 扑克牌中的顺子
     * 从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。A 不能视为 14。
     * 示例 1:
     * 输入: [1,2,3,4,5]
     * 输出: True
     * 示例 2:
     * 输入: [0,0,1,2,5]
     * 输出: True
     *
     * @param nums
     * @return
     */
    public boolean isStraight(int[] nums) {
        Set<Integer> hashSet = new HashSet<>();
        int max = 0, min = 14;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) continue;
            max = Math.max(max, nums[i]);
            min = Math.min(min, nums[i]);
            if (hashSet.contains(nums[i])) return false;
            hashSet.add(nums[i]);
        }
        return max - min < 5;
    }

    /**
     * 面试题62. 圆圈中最后剩下的数字
     * 0,1,,n-1这n个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字。求出这个圆圈里剩下的最后一个数字。
     * 例如，0、1、2、3、4这5个数字组成一个圆圈，从数字0开始每次删除第3个数字，则删除的前4个数字依次是2、0、4、1，因此最后剩下的数字是3。
     * 示例 1：
     * 输入: n = 5, m = 3
     * 输出: 3
     *
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining(int n, int m) {
        int ans = 0;
        for (int i = 2; i <= n; i++) {
            ans = (ans + m) % i;
        }
        return ans;
    }

    /**
     * 面试题63. 股票的最大利润
     * 假设把某股票的价格按照时间先后顺序存储在数组中，请问买卖该股票一次可能获得的最大利润是多少？
     * 示例 1:
     * 输入: [7,1,5,3,6,4]
     * 输出: 5
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int[] dp = new int[prices.length];
        dp[0] = prices[0];
        for (int i = 1; i < prices.length; i++) {

        }
        return dp[dp.length];
    }

    /**
     * 面试题64. 求1+2+…+n
     * 求 1+2+...+n ，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
     *
     * @param n
     * @return
     */
    public int sumNums(int n) {
        if (n == 1) {
            return 1;
        }
        return sumNums(n - 1) + (n - 1);
    }

    /**
     * 面试题65. 不用加减乘除做加法
     * 写一个函数，求两个整数之和，要求在函数体内不得使用 “+”、“-”、“*”、“/” 四则运算符号。
     * 示例:
     * 输入: a = 1, b = 1
     * 输出: 2
     *
     * @param a
     * @param b
     * @return
     */
    public int add(int a, int b) {
        while (b != 0) {
            int i = a ^ b;
            int i1 = (a & b) << 1;
            a = i;
            b = i1;
        }
        return a;
    }

    /**
     * 面试题66. 构建乘积数组给定一个数组 A[0,1,…,n-1]，请构建一个数组 B[0,1,…,n-1]，其中 B 中的元素 B[i]=A[0]×A[1]×…×A[i-1]×A[i+1]×…×A[n-1]。不能使用除法。
     * 输入: [1,2,3,4,5]
     * 示例:
     * 输出: [120,60,40,30,24]
     *
     * @param a
     * @return
     */
    public int[] constructArr(int[] a) {
        int[] arr = new int[a.length];
        int left = 1;
        for (int i = 0; i < a.length; i++) {
            arr[i] = left;
            left *= a[i];
        }
        int right = 1;
        for (int i = a.length - 1; i >= 0; i--) {
            arr[i] *= right;
            right *= a[i];
        }
        return arr;
    }

    /**
     * 面试题67. 把字符串转换成整数
     * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
     * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
     * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
     * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
     * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
     *
     * @param str
     * @return
     */
    public int strToInt(String str) {
        char[] PASS_PERMIT = new char[]{'-'};
        int ans = 0;
        if (str == null || str.trim().length() == 0) {
            return 0;
        }
        String s = str.trim();
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
            }
        }
        return ans;
    }

    /**
     * 面试题68 - I. 二叉搜索树的最近公共祖先
     * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
     * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
     * 例如，给定如下二叉搜索树:  root = [6,2,8,0,4,7,9,null,null,3,5]
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            if (p.val < root.val && q.val < root.val) {
                root = root.left;
            } else if (p.val > root.val && q.val > root.val) {
                root = root.right;
            } else {
                return root;
            }
        }
        return root;
    }

    /**
     * 面试题68 - II. 二叉树的最近公共祖先
     * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
     * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
     * 示例 1:
     * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
     * 输出: 3
     * 解释: 节点 5 和节点 1 的最近公共祖先是节点 3。
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left == null) return right;
        if (right == null) return left;
        return root;
    }
}
