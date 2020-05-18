package com.zxw.leetcode.swordoffer;

import com.zxw.common.datastruct.ListNode;
import com.zxw.common.datastruct.TreeNode;
import com.zxw.leetcode.swordoffer.data.MinStack;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zxw
 * @date 2020-05-11 08:8:46
 * @Description 牛客练习
 */
public class Test {

    public static void main(String[] args) {
        int n = 39;
        System.out.println(Integer.toBinaryString(10));
        System.out.println(Integer.toBinaryString(100));
        while (n != 0) {
            n = n & (n - 1);
            System.out.println(n);
        }
        Test t = new Test();
        t.movingCount(4, 6, 15);
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        listNode.next.next.next = new ListNode(4);
        listNode.next.next.next.next = new ListNode(5);
        t.getKthFromEnd(listNode, 2);
        MinStack minStack = new MinStack();
        minStack.push(0);
        minStack.push(-2);
        minStack.push(-3);
        minStack.min();
        minStack.pop();
        minStack.min();
        minStack.pop();
        minStack.min();
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
        return null;
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
        while (!queue.isEmpty()) {
            List<Integer> res = new ArrayList<>();
            for (int i = queue.size(); i > 0; i--) {
                TreeNode poll = queue.poll();
                res.add(poll.val);
                if (poll.left != null) queue.add(poll.left);
                if (poll.right != null) queue.add(poll.right);
            }
            list.add(res);
        }
        return list;
    }
}
