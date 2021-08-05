package com.zxw.leetcode.interviewgold;

import java.util.HashSet;

/**
 * @author zxw
 * @date 2020/7/8 8:59
 */
public class StringText {
    public static void main(String[] args) {
        StringText stringText = new StringText();

    }

    /**
     * 面试题 01.01. 判定字符是否唯一
     * 实现一个算法，确定一个字符串 s 的所有字符是否全都不同。
     * 示例 1：
     * 输入: s = "leetcode"
     * 输出: false
     *
     * @param astr
     * @return
     */
    public boolean isUnique(String astr) {
        int[] arr = new int[128];
        for (int i = 0; i < astr.length(); i++) {
            if (arr[astr.charAt(i)] != 0) {
                return false;
            }
            arr[astr.charAt(i)]++;
        }
        return true;
    }

    /**
     * 面试题 01.02. 判定是否互为字符重排
     * 给定两个字符串 s1 和 s2，请编写一个程序，确定其中一个字符串的字符重新排列后，能否变成另一个字符串。
     *
     * 示例 1：
     *
     * 输入: s1 = "abc", s2 = "bca"
     * 输出: true
     * @param s1
     * @param s2
     * @return
     */
    public boolean CheckPermutation(String s1, String s2) {
        if(s1.length() != s2.length()){
            return false;
        }
        int[] arr = new int[128];
        for (int i = 0; i < s1.length(); i++) {
            arr[s1.charAt(i)]++;
        }

        for (int i = 0; i < s2.length(); i++) {
            if(arr[s2.charAt(i)] == 0){
                return false;
            }
            arr[s2.charAt(i)]--;
        }
        return true;
    }

    /**
     * 面试题 01.03. URL化
     * URL化。编写一种方法，将字符串中的空格全部替换为%20。假定该字符串尾部有足够的空间存放新增字符，并且知道字符串的“真实”长度。（注：用Java实现的话，请使用字符数组实现，以便直接在数组上操作。）
     * <p>
     * 示例1:
     * <p>
     * 输入："Mr John Smith    ", 13
     * 输出："Mr%20John%20Smith"
     *
     * @param S
     * @param length
     * @return
     */
    public String replaceSpaces(String S, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == ' ') {
                sb.append("%20");
            } else {
                sb.append(S.charAt(i));
            }
            if (i == length - 1) {
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 面试题 01.04. 回文排列
     * 给定一个字符串，编写一个函数判定其是否为某个回文串的排列之一。
     * 回文串是指正反两个方向都一样的单词或短语。排列是指字母的重新排列。
     * 回文串不一定是字典当中的单词。
     * 示例1：
     * 输入："tactcoa"
     * 输出：true（排列有"tacocat"、"atcocta"，等等）
     *
     * @param s
     * @return
     */
    public boolean canPermutePalindrome(String s) {
        HashSet<Character> set = new HashSet();
        for (int i = 0; i < s.length(); i++) {
            if (set.contains(s.charAt(i))) {
                set.remove(s.charAt(i));
            } else {
                set.add(s.charAt(i));
            }
        }
        return set.size() <= 1;
    }

    /**
     * 面试题 01.05. 一次编辑
     * 字符串有三种编辑操作:插入一个字符、删除一个字符或者替换一个字符。 给定两个字符串，编写一个函数判定它们是否只需要一次(或者零次)编辑。
     * 示例 1:
     * 输入:
     * first = "pale"
     * second = "ple"
     * 输出: True
     *
     * @param first
     * @param second
     * @return
     */
    public boolean oneEditAway(String first, String second) {
        oneEditAwayDFS(first, second, first.length() - 1, second.length() - 1);
        int[][] dp = new int[first.length() + 1][second.length() + 1];
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = min(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[first.length()][second.length()] <= 1;
    }

    public int oneEditAwayDFS(String first, String second, int l1, int l2) {
        if (l1 == -1) {
            return l2 + 1;
        }
        if (l2 == -1) {
            return l1 + 1;
        }
        if (first.charAt(l1) == second.charAt(l2)) {
            return oneEditAwayDFS(first, second, --l1, --l2);
        } else {
            return min(oneEditAwayDFS(first, second, l1 - 1, l2 - 1) + 1,
                    oneEditAwayDFS(first, second, l1, l2 - 1) + 1,
                    oneEditAwayDFS(first, second, l1 - 1, l2) + 1);
        }
    }

    /**
     * 面试题 01.06. 字符串压缩
     * 字符串压缩。利用字符重复出现的次数，编写一种方法，实现基本的字符串压缩功能。比如，字符串aabcccccaaa会变为a2b1c5a3。若“压缩”后的字符串没有变短，则返回原先的字符串。你可以假设字符串中只包含大小写英文字母（a至z）。
     * <p>
     * 示例1:
     * <p>
     * 输入："aabcccccaaa"
     * 输出："a2b1c5a3"
     *
     * @param S
     * @return
     */
    public String compressString(String S) {
        if(S.length() <= 1){
            return S;
        }
        StringBuilder sb = new StringBuilder();
        char ch = S.charAt(0);
        int len = 1;
        sb.append(ch);
        for (int i = 1; i < S.length(); i++) {
            if (S.charAt(i) == ch) {
                len++;
            } else {
                sb.append(len);
                len = 1;
                ch = S.charAt(i);
                sb.append(ch);
            }
        }
        sb.append(len);
        return sb.toString().length() > S.length() ? S : sb.toString();
    }

    /**
     * 对角线旋转，中点旋转
     * @param matrix
     */
    public void rotate(int[][] matrix) {

    }

    public static int min(int var1, int var2, int var3) {
        return var1 < var2 ? var1 < var3 ? var1 : var3 : var2 < var3 ? var2 : var3;
    }

    /**
     * 面试题 01.09. 字符串轮转
     * 字符串轮转。给定两个字符串s1和s2，请编写代码检查s2是否为s1旋转而成（比如，waterbottle是erbottlewat旋转后的字符串）。
     * 示例1:
     * 输入：s1 = "waterbottle", s2 = "erbottlewat"
     * 输出：True
     *
     * @param s1
     * @param s2
     * @return
     */
    public boolean isFlipedString(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        if (s1.equals(s2)) {
            return true;
        }
        s1 = s1 + s2;
        return s1.contains(s2);
    }
}
