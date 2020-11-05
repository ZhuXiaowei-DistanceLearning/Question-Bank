package com.zxw.leetcode.algorithm;

import com.zxw.common.datastruct.TreeNode;

import java.util.*;

/**
 * @author zxw
 * @date 2020/8/7 9:21
 */
public class AugustDay {
    public static void main(String[] args) {
        AugustDay augustDay = new AugustDay();
        augustDay.isValid("");
        augustDay.reverseWords("I love u");
        TreeNode node = new TreeNode(10);
        List<String> list = new ArrayList<>();
        List<String> integerList = new ArrayList<>();
        integerList.add("1");
        integerList.add("2");
        integerList.add("3");
        integerList.add("4");
        integerList.add("5");
        integerList.add("6");
        System.out.println(5%3);
        System.out.println(5%4);
        System.out.println(5%5);
        System.out.println(5%6);
        System.out.println(5%7);
        System.out.println(8%4);
        System.out.println(list.toArray());
        Date date = new Date(2020, 10, 27, 20, 36, 22);
        Date date2 = new Date(2020, 10, 27,0,0,0);
        System.out.println(date.getTime() - date2.getTime());
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
     * 输出: true
     *
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q != null) {
            return false;
        }
        if (p != null && q == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        if (p == null && q == null) {
            return true;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * 20. 有效的括号
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     * 有效字符串需满足：
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 注意空字符串可被认为是有效字符串。
     * 示例 1:
     * 输入: "()"
     * 输出: true
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        if (s.length() == 1) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap();

        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                char c1 = stack.empty() ? ' ' : stack.pop();
                if (c1 != map.get(c)) {
                    return false;
                }
            } else {
                stack.push(c);
            }
        }
        return true;
    }

    /**
     * 733. 图像渲染
     * 有一幅以二维整数数组表示的图画，每一个整数表示该图画的像素值大小，数值在 0 到 65535 之间。
     * 给你一个坐标 (sr, sc) 表示图像渲染开始的像素值（行 ，列）和一个新的颜色值 newColor，让你重新上色这幅图像。
     * 为了完成上色工作，从初始坐标开始，记录初始坐标的上下左右四个方向上像素值与初始坐标相同的相连像素点，接着再记录这四个方向上符合条件的像素点与他们对应四个方向上像素值与初始坐标相同的相连像素点，……，重复该过程。将所有有记录的像素点的颜色值改为新的颜色值。
     * 最后返回经过上色渲染后的图像。
     * 示例 1:
     * 输入:
     * image = [[1,1,1],[1,1,0],[1,0,1]]
     * sr = 1, sc = 1, newColor = 2
     * 输出: [[2,2,2],[2,2,0],[2,0,1]]
     * 解析:
     * 在图像的正中间，(坐标(sr,sc)=(1,1)),
     * 在路径上所有符合条件的像素点的颜色都被更改成2。
     * 注意，右下角的像素没有更改为2，
     * 因为它不是在上下左右四个方向上与初始点相连的像素点。
     *
     * @param image
     * @param sr
     * @param sc
     * @param newColor
     * @return
     */
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int origColor = image[sr][sc];
        fill(image, sr, sc, origColor, newColor);
        return image;
    }

    void fill(int[][] image, int x, int y,
              int origColor, int newColor) {
        // 出界：超出数组边界
        if (!inArea(image, x, y)) return;
        // 碰壁：遇到其他颜色，超出 origColor 区域
        if (image[x][y] != origColor) return;
        // 已探索过的 origColor 区域
        if (image[x][y] == -1) return;

        // choose：打标记，以免重复
        image[x][y] = -1;
        fill(image, x, y + 1, origColor, newColor);
        fill(image, x, y - 1, origColor, newColor);
        fill(image, x - 1, y, origColor, newColor);
        fill(image, x + 1, y, origColor, newColor);
        // unchoose：将标记替换为 newColor
        image[x][y] = newColor;
    }

    boolean inArea(int[][] image, int x, int y) {
        return x >= 0 && x < image.length
                && y >= 0 && y < image[0].length;
    }

    /**
     * 110. 平衡二叉树
     * 给定一个二叉树，判断它是否是高度平衡的二叉树。
     * <p>
     * 本题中，一棵高度平衡二叉树定义为：
     * <p>
     * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。
     * <p>
     * 示例 1:
     * <p>
     * 给定二叉树 [3,9,20,null,null,15,7]
     * <p>
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     *
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        return isBalancedDFS(root) != -1;
    }

    public int isBalancedDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int var1 = isBalancedDFS(root.left);
        if (var1 == -1) {
            return -1;
        }
        int var2 = isBalancedDFS(root.right);
        if (var2 == -1) {
            return -1;
        }
        return Math.abs(var1 - var2) < 2 ? Math.max(var1, var2) + 1 : -1;
    }

    /**
     * 647. 回文子串
     * 给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
     * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
     *   回文串主要考虑奇偶数情况，从当前字符两边扩散
     * 示例 1：
     * 输入："abc"
     * 输出：3
     * 解释：三个回文子串: "a", "b", "c"
     *
     * @param s
     * @return
     */
    public int countSubstrings(String s) {
        int ans = 0;
        if (s.length() == 0 || s.trim().length() == 0 || s == null) {
            return 0;
        }
        for (int i = 0; i < s.length(); i++) {
            // 偶数
            ans += countSubstringsDFS(s, i, i);
            // 奇数
            ans += countSubstringsDFS(s, i, i + 1);
        }
        ans += s.length();
        return ans;
    }

    public int countSubstringsDFS(String s, int l, int r) {
        int ans = 0;
        // 从当前数两端开始判断
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            if (l == r) {
                ans -= 1;
            }
            l--;
            r++;
            ans += 1;
        }
        return ans;
    }

    /**
     * 111. 二叉树的最小深度
     * 给定一个二叉树，找出其最小深度。
     * <p>
     * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     * <p>
     * 说明: 叶子节点是指没有子节点的节点。
     * <p>
     * 示例:
     * <p>
     * 给定二叉树 [3,9,20,null,null,15,7],
     * <p>
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     * 返回它的最小深度  2.
     *
     * @param root
     * @return
     */
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        int m1 = minDepth(root.left);
        int m2 = minDepth(root.right);
        //1.如果左孩子和右孩子有为空的情况，直接返回m1+m2+1
        //2.如果都不为空，返回较小深度+1
        return root.left == null || root.right == null ? m1 + m2 + 1 : Math.min(m1, m2) + 1;
    }


    /**
     * 459. 重复的子字符串
     * 给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。给定的字符串只含有小写英文字母，并且长度不超过10000。
     * 示例 1:
     * 输入: "abab"
     * 输出: True
     * 解释: 可由子字符串 "ab" 重复两次构成。
     *
     * @param s
     * @return
     */
    public boolean repeatedSubstringPattern(String s) {
        return (s + s).indexOf(s, 1) != s.length();
    }

    /**
     * 491. 递增子序列
     * 给定一个整型数组, 你的任务是找到所有该数组的递增子序列，递增子序列的长度至少是2。
     * 示例:
     * 输入: [4, 6, 7, 7]
     * 输出: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            List<Integer> list = new ArrayList<>();
            list.add(nums[i]);
            for (int j = i + 1; j < nums.length - 1; j++) {
                if (nums[j] >= nums[i]) {
                    list.add(nums[j]);
                    if (list.size() >= 2) {
                        res.add(list);
                    }
                }
            }
        }
        return res;
    }

    /**
     * 657. 机器人能否返回原点
     * 在二维平面上，有一个机器人从原点 (0, 0) 开始。给出它的移动顺序，判断这个机器人在完成移动后是否在 (0, 0) 处结束。
     * 移动顺序由字符串表示。字符 move[i] 表示其第 i 次移动。机器人的有效动作有 R（右），L（左），U（上）和 D（下）。如果机器人在完成所有动作后返回原点，则返回 true。否则，返回 false。
     * 注意：机器人“面朝”的方向无关紧要。 “R” 将始终使机器人向右移动一次，“L” 将始终向左移动等。此外，假设每次移动机器人的移动幅度相同。
     *  
     * 示例 1:
     * 输入: "UD"
     * 输出: true
     * 解释：机器人向上移动一次，然后向下移动一次。所有动作都具有相同的幅度，因此它最终回到它开始的原点。因此，我们返回 true。
     *
     * @param moves
     * @return
     */
    public boolean judgeCircle(String moves) {
        int up = 0;
        int down = 0;
        int left = 0;
        int right = 0;
        for (int i = 0; i < moves.length(); i++) {
            if (moves.charAt(i) == 'U') {
                up += 1;
            } else if (moves.charAt(i) == 'L') {
                left += 1;
            } else if (moves.charAt(i) == 'R') {
                right += 1;
            } else if (moves.charAt(i) == 'D') {
                down += 1;
            }
        }
        return (up == down) && (left == right);
    }

    /**
     * 557. 反转字符串中的单词 III
     * 给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
     * 示例：
     * 输入："Let's take LeetCode contest"
     * 输出："s'teL ekat edoCteeL tsetnoc"
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        if (s == null) {
            return s;
        }
        String[] split = s.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(split[i]);
            sb.append(sb2.reverse());
            if (i == split.length - 1) {
                continue;
            }
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * 841. 钥匙和房间
     * 有 N 个房间，开始时你位于 0 号房间。每个房间有不同的号码：0，1，2，...，N-1，并且房间里可能有一些钥匙能使你进入下一个房间。
     * 在形式上，对于每个房间 i 都有一个钥匙列表 rooms[i]，每个钥匙 rooms[i][j] 由 [0,1，...，N-1] 中的一个整数表示，其中 N = rooms.length。 钥匙 rooms[i][j] = v 可以打开编号为 v 的房间。
     * 最初，除 0 号房间外的其余所有房间都被锁住。
     * 你可以自由地在房间之间来回走动。
     * 如果能进入每个房间返回 true，否则返回 false。
     * 示例 1：
     * 输入: [[1],[2],[3],[]]
     * 输出: true
     * 解释:
     * 我们从 0 号房间开始，拿到钥匙 1。
     * 之后我们去 1 号房间，拿到钥匙 2。
     * 然后我们去 2 号房间，拿到钥匙 3。
     * 最后我们去了 3 号房间。
     * 由于我们能够进入每个房间，我们返回 true。
     *
     * @param rooms
     * @return
     */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        boolean[] marked = new boolean[rooms.size()];
        dfscanVisitAllRooms(0, marked, rooms);
        for (boolean b : marked) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    public void dfscanVisitAllRooms(int i, boolean[] marked, List<List<Integer>> rooms) {
        marked[i] = true;
        for (Integer integer : rooms.get(i)) {
            if (!marked[integer]) {
                dfscanVisitAllRooms(integer, marked, rooms);
            }
        }
    }

    /**
     * 257. 二叉树的所有路径
     * 给定一个二叉树，返回所有从根节点到叶子节点的路径。
     * 说明: 叶子节点是指没有子节点的节点。
     * 输入:
     * 示例:
     * 1
     * /   \
     * 2     3
     * \
     * 5
     * 输出: ["1->2->5", "1->3"]
     * 解释: 所有根节点到叶子节点的路径为: 1->2->5, 1->3
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        dfs(root, "", res);
        return res;
    }

    private void dfs(TreeNode root, String path, List<String> res) {
        //如果为空，直接返回
        if (root == null)
            return;
        //如果是叶子节点，说明找到了一条路径，把它加入到res中
        if (root.left == null && root.right == null) {
            res.add(path + root.val);
            return;
        }
        //如果不是叶子节点，在分别遍历他的左右子节点
        dfs(root.left, path + root.val + "->", res);
        dfs(root.right, path + root.val + "->", res);
    }

    /**
     * 977. 有序数组的平方
     * 给定一个按非递减顺序排序的整数数组 A，返回每个数字的平方组成的新数组，要求也按非递减顺序排序。
     *  
     * 示例 1：
     * 输入：[-4,-1,0,3,10]
     * 输出：[0,1,9,16,100]
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/squares-of-a-sorted-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param A
     * @return
     */
    public int[] sortedSquares(int[] A) {
        for (int i = 0; i < A.length; i++) {
            A[i] *= A[i];
        }
        Arrays.sort(A);
        return A;
    }

    /**
     * 1207. 独一无二的出现次数
     * 给你一个整数数组 arr，请你帮忙统计数组中每个数的出现次数。
     * 如果每个数的出现次数都是独一无二的，就返回 true；否则返回 false。
     *  
     * 示例 1：
     * 输入：arr = [1,2,2,1,1,3]
     * 输出：true
     * 解释：在该数组中，1 出现了 3 次，2 出现了 2 次，3 只出现了 1 次。没有两个数的出现次数相同。
     *
     * @param arr
     * @return
     */
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
        }
        return map.size() == new HashSet<>(map.values()).size();
    }
}
