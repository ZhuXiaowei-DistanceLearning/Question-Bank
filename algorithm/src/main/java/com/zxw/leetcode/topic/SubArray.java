package com.zxw.leetcode.topic;

import com.zxw.leetcode.type.tree.LeetCodeWrapper;
import org.springframework.util.Assert;

/**
 * @author zxw
 * @date 2022/7/27 9:33
 */
public class SubArray {
    public static void main(String[] args) {
        SubArray subArray = new SubArray();
        Assert.isTrue(subArray.maxProduct(LeetCodeWrapper.stringToIntegerArray("[-1,-2,-9,-6]")) == 108, "预期结果错误");
        Assert.isTrue(subArray.maxProduct(LeetCodeWrapper.stringToIntegerArray("[2,3,-2,4]")) == 6, "预期结果错误");
    }

    /**
     * 152.乘积最大子数组
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        if (nums.length < 1) {
            return 0;
        }
        int[] dp = new int[nums.length];
        int[] min = new int[nums.length];
        int[] max = new int[nums.length];
        dp[0] = nums[0];
        min[0] = nums[0];
        max[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            min[i] = Math.min(max[i - 1] * nums[i], Math.min(min[i - 1] * nums[i], nums[i]));
            max[i] = Math.max(min[i - 1] * nums[i], Math.max(max[i - 1] * nums[i], nums[i]));
            dp[i] = Math.max(dp[i - 1], Math.max(min[i], max[i]));
        }
        return dp[nums.length - 1];
    }
}
