package com.zxw.leetcode.topic;

import cn.hutool.core.lang.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * @author zxw
 * @date 2022/6/29 10:07
 */
public class Parentheses {

    public static void main(String[] args) {
        Parentheses parentheses = new Parentheses();
        parentheses.minInsertions("(()))(()))()())))", 4);
        parentheses.minInsertions("()())))()", 3);
        parentheses.minInsertions("((((((", 12);
        parentheses.minInsertions(")))))))", 5);
        parentheses.minAddToMakeValid("())");
    }

    /**
     * 32. 最长有效括号
     *
     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {
        if (s.length() <= 1) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>();
        // dp[i] 的定义：记录以 s[i-1] 结尾的最长合法括号子串长度
        int[] dp = new int[s.length() + 1];
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                // 只要碰到左括号，不可能是合法的
                stack.push(i);
                dp[i + 1] = 0;
            } else {
                // 遇到右括号
                if (!stack.isEmpty()) {
                    // 配对的左括号对应索引
                    int leftIndex = stack.pop();
                    // 以这个右括号结尾的最长子串长度
                    // i- leftIndex：当前子串的最长合法长度
                    // 1：下标从0开始
                    //  dp[leftIndex]：此位置累计的合法长度
                    int len = 1 + i - leftIndex + dp[leftIndex];
                    dp[i + 1] = len;
                } else {
                    // 没有配对的左括号
                    dp[i + 1] = 0;
                }
            }
        }
        // 计算最长子串的长度
        int res = 0;
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /**
     * 1541. 平衡括号字符串的最少插入次数
     *
     * @param s
     * @return
     */
    public int minInsertions(String s, int sum) {
        int res = 0;
        int need = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                need += 2;
                if (need % 2 == 1) {
                    // 插入一个右括号
                    res++;
                    // 对右括号的需求减一
                    need--;
                }
            }
            if (s.charAt(i) == ')') {
                need--;
                if (need == -1) {
                    need = 1;
                    res++;
                }
            }
        }
        Assert.isTrue(sum == res + need);
        return res + need;
    }


    /**
     * 921. 使括号有效的最少添加
     *
     * @param s
     * @return
     */
    public int minAddToMakeValid(String s) {
        int res = 0;
        int need = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                need++;
            }
            if (s.charAt(i) == ')') {
                need--;
                if (need < 0) {
                    need = 0;
                    res++;
                }
            }
        }
        // res 记录的左括号的插入次数，need 记录了右括号的需求，当 for 循环结束后，若 need 不为 0，那么就意味着右括号还不够，需要插入。
        return res + need;
    }

    public int minAddToMakeValid2(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int res = 0;
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else {
                if (!stack.isEmpty()) {
                    stack.pop();
                } else {
                    res++;
                }
            }
        }
        // 插入的右括号数量 + 多余的左括号数量
        return res + stack.size();
    }

    public List<String> generateParenthesis(int n) {
        if (n == 0) {
            return Collections.EMPTY_LIST;
        }
        List<String> res = new ArrayList<>();
        generateParenthesisDFS(n, n, "", res);
        return res;
    }

    /**
     * 1、一个「合法」括号组合的左括号数量一定等于右括号数量，这个很好理解。
     * <p>
     * 2、对于一个「合法」的括号字符串组合 p，必然对于任何 0 <= i < len(p) 都有：子串 p[0..i] 中左括号的数量都大于或等于右括号的数量。
     *
     * @param l：左括号剩余数量
     * @param r：右括号剩余数量
     * @return
     */
    public void generateParenthesisDFS(int l, int r, String s, List<String> res) {
        // 对于任意子串，如果右边括号大于左边括号数，那么一定不合法
        if (l > r) {
            return;
        }
        // 如果可用括号数为0了，那么也一定不合法
        if (l < 0 || r < 0) {
            return;
        }
        if (l == 0 && r == 0) {
            res.add(s);
            return;
        }
        String t = s;
        generateParenthesisDFS(l - 1, r, t += "(", res);
        t = s;
        generateParenthesisDFS(l, r - 1, t += ")", res);
    }
}
