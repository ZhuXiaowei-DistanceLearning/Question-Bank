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
        List<String> list = new ArrayList<>();
        System.out.println(list.toArray());
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
}
