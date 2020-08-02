package com.zxw.lintcode;

import com.zxw.common.datastruct.TreeNode;

import javax.swing.plaf.SliderUI;
import java.util.HashSet;

/**
 * @author zxw
 * @date 2020/6/24 14:07
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(7 % 9);
        System.out.println(8 % 9);
        System.out.println(9 % 9);
        System.out.println(10 % 9);
        System.out.println(11 % 9);
        System.out.println(12 % 9);
        System.out.println(13 % 9);
        System.out.println(22 % 9);
        Test test = new Test();
        test.strStr("mississippi", "issip");
        test.singleNumber(new int[]{1, 1, 2, 2, 3, 4, 4, 5, 5});
    }

    /**
     * 给一个整数数组，找到两个数使得他们的和等于一个给定的数 target。
     * 你需要实现的函数twoSum需要返回这两个数的下标, 并且第一个下标小于第二个下标。注意这里下标的范围是 0 到 n-1。
     * 样例
     * Example1:
     * 给出 numbers = [2, 7, 11, 15], target = 9, 返回 [0, 1].
     *
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum(int[] numbers, int target) {
        // write your code here
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                if (i == j) {
                    continue;
                }
                if (numbers[i] + numbers[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{0, 0};
    }

    /**
     * 1. A + B 问题
     * 给出两个整数 aa 和 bb , 求他们的和。
     */
    public class Solution {
        /**
         * @param a: An integer
         * @param b: An integer
         * @return: The sum of a and b
         */
        public int aplusb(int a, int b) {
            // write your code here
            // 不进位假发
            while (b != 0) {
                int c = a ^ b;
                int d = (a & b << 1);
                a = c;
                b = d;
            }
            return a;
        }
    }

    /**
     * 82. 落单的数
     * 给出 2 * n + 1个数字，除其中一个数字之外其他每个数字均出现两次，找到这个数字。
     * <p>
     * 样例
     * 样例 1:
     * <p>
     * 输入：[1,1,2,2,3,4,4]
     * 输出：3
     * 解释：
     * 仅3出现一次
     *
     * @param A: An integer array
     * @return: An integer
     */
    public int singleNumber(int[] A) {
        // write your code here
        int ans = 0;
        for (int i = 0; i < A.length; i++) {
            ans ^= A[i];
        }
        return ans;
    }

    /**
     * 给定一个字符串（以字符数组的形式给出）和一个偏移量，根据偏移量原地旋转字符串(从左向右旋转)。
     * 样例
     * 样例 1:
     * 输入:  str="abcdefg", offset = 3
     * 输出:  str = "efgabcd"
     * 样例解释:  注意是原地旋转，即str旋转后为"efgabcd"
     *
     * @param str:    An array of char
     * @param offset: An integer
     * @return: nothing
     */
    public void rotateString(char[] str, int offset) {
        // write your code here
        char temp;
        if (offset == 0) return;
        if (str.length == 0) return;
        int len = str.length;
        for (int i = 1; i <= offset % len; i++) {
            temp = str[len - 1];
            int j = len - 2;
            while (j >= 0) {
                str[j + 1] = str[j];
                j--;
            }
            str[0] = temp;
        }
    }

    /**
     * 1776. 梯形的面积
     * cat-only-icon
     * CAT 专属题目
     * 中文English
     * 梯形面积的计算公式是(a + b) * h / 2;现在给出a, b, h。返回梯形的面积。
     * 样例 1：
     * 输入 : a = 2, b = 4, h = 4
     * 输出 : 12
     * 解析：area = (2 + 4) * 4 / 2 = 12
     *
     * @param a:
     * @param b:
     * @param h:
     * @return: Return the area of trapezoid
     */
    public double AreaOfTrapezoid(int a, int b, int h) {
        return ((double) a + (double) b) * h / 2;
    }

    /**
     * 13. 字符串查找
     * 中文English
     * 对于一个给定的 source 字符串和一个 target 字符串，你应该在 source 字符串中找出 target 字符串出现的第一个位置(从0开始)。如果不存在，则返回 -1。
     * <p>
     * 样例
     * 样例 1:
     * <p>
     * 输入: source = "source" ， target = "target"
     * 输出:-1
     * 样例解释: 如果source里没有包含target的内容，返回-1
     *
     * @param source
     * @param target
     * @return
     */
    public int strStr(String source, String target) {
        if (source == null || target == null) {
            return -1;
        }

        for (int i = 0; i < source.length() - target.length() + 1; i++) {
            int j = 0;
            for (j = 0; j < target.length(); j++) {
                if (source.charAt(i + j) != target.charAt(j)) {
                    break;
                }
            }

            if (j == target.length()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 中文English
     * 实现一个算法确定字符串中的字符是否均唯一出现
     * 样例
     * 样例 1:
     * 输入:  "abc_____"
     * 输出:  false
     *
     * @param str
     * @return
     */
    public boolean isUnique(String str) {
        char[] c = new char[128];
        for (int i = 0; i < str.length(); i++) {
            if (c[str.charAt(i)] == 0) {
                c[str.charAt(i)]++;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 53. 翻转字符串中的单词
     * 中文English
     * 给定一个字符串，逐个翻转字符串中的每个单词。
     * 样例
     * 样例  1:
     * 输入:  "the sky is blue"
     * 输出:  "blue is sky the"
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        // write your code here
        if (s.length() == 0) {
            return s;
        }
        String[] split = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = split.length - 1; i >= 0; i--) {
            if (!split[i].isEmpty()) {
                sb.append(split[i]);
                if (i != 0) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
}
