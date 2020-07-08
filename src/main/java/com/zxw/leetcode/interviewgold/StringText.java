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
}
