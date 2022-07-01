package com.zxw.leetcode.topic;

import java.util.Arrays;

/**
 * https://labuladong.github.io/algo/4/29/105/
 * @author zxw
 * @date 2022/6/28 16:08
 */
public class FullArray {
    public static void main(String[] args) {
        FullArray fullArray = new FullArray();
        // 5,5,2,3,4,7
        // 5,5,4,3,2,7
        int[] nums = {1,2,3};
        fullArray.nextPermutation(nums);
        Arrays.stream(nums).forEach(System.out::println);
    }

    /**
     * 后向前查找第一个相邻升序的元素对 (i,j)，满足 A[i] < A[j]。此时 [j,end) 必然是降序
     * 在 [j,end) 从后向前查找第一个满足 A[i] < A[k] 的 k。A[i]、A[k] 分别就是上文所说的「小数」、「大数」
     * 将 A[i] 与 A[k] 交换
     * 可以断定这时 [j,end) 必然是降序，逆置 [j,end)，使其升序
     * 如果在步骤 1 找不到符合的相邻元素对，说明当前 [begin,end) 为一个降序顺序，则直接跳到步骤 4
     *
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        int len = nums.length;
        int i = len - 2;
        int j = len - 1;
        int k = len - 1;
        while (i >= 0 && nums[i] >= nums[j]) {
            i--;
            j--;
        }//从后往前找
        if (i >= 0) {//不是最后一个序列
            while (nums[i] >= nums[k]) {
                k--;
            }
            swap(nums, i, k);
        }
        reverse(nums, j, len - 1);
    }

    public void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;

    }

    public void reverse(int[] nums, int a, int b) {
        while (a < b) {
            swap(nums, a++, b--);
        }
    }
}
