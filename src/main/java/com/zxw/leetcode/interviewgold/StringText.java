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
}
