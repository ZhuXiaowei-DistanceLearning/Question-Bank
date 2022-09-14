package com.zxw.leetcode.topic;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author zxw
 * @date 2022/9/14 16:00
 */
public class OneStack {
    public static void main(String[] args) {
        OneStack stack = new OneStack();
    }

    /**
     * 739. 每日温度
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int[] res = new int[temperatures.length];
        Stack<Integer> stack = new Stack();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = temperatures.length - 1; i >= 0; i--) {
            map.put(temperatures[i], i);
            while (!stack.isEmpty() && stack.peek() <= temperatures[i]) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? 0 : map.get(stack.peek()) - i;
            stack.push(temperatures[i]);
        }
        return res;
    }


    /**
     * 496. 下一个更大元素 I
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap();
        int[] nums = nextGet(nums2);
        for (int i = 0; i < nums2.length; i++) {
            map.put(nums2[i], nums[i]);
        }
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            res[i] = map.get(nums1[i]);
        }
        return res;
    }

    public int[] nextGet(int[] nums) {
        int[] res = new int[nums.length];
        Stack<Integer> stack = new Stack();
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() < nums[i]) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i]);
        }
        return res;
    }
}
