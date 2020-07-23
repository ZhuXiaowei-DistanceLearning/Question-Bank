package com.zxw.lintcode;

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
}
