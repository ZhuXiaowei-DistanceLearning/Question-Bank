package com.zxw.leetcode.topic;

import cn.hutool.core.lang.Assert;
import com.zxw.leetcode.type.tree.LeetCodeWrapper;

/**
 * @author zxw
 * @date 2022/7/22 15:26
 */
public class DP {
    public static void main(String[] args) {
        DP dp = new DP();
        Assert.isTrue(4 == dp.rob2(LeetCodeWrapper.stringToIntegerArray("[1,2,3,1]")));
//        Assert.isTrue(4 == dp.rob(LeetCodeWrapper.stringToIntegerArray("[1,2,3,1]")));
//        Assert.isTrue(5 == dp.maxProfit(LeetCodeWrapper.stringToIntegerArray("[7,1,5,3,6,4]")));
    }

    /**
     * 213. 打家劫舍 II
     * 关键点在于第二天的值，如果第二天小于第一天，那就没必要购买了
     *
     * @param nums
     * @return
     */
    public int rob2(int[] nums) {
        if (nums.length <= 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        return Math.max(rob2(nums, 0, nums.length - 1), rob2(nums, 1, nums.length));
    }

    public int rob2(int[] nums, int start, int end) {
        if (end <= 1) {
            return nums[start];
        }
        if (end == 2) {
            return Math.max(nums[start], nums[start + 1]);
        }
        int legnth = end - start;
        int[] dp = new int[legnth];
        dp[0] = nums[start];
        dp[1] = Math.max(nums[start + 1], dp[0]);
        for (int i = 2; i < legnth; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i + start], dp[i - 1]);
        }
        return dp[legnth - 1];
    }

    /**
     * 198. 打家劫舍
     * 关键点在于第二天的值，如果第二天小于第一天，那就没必要购买了
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        if (nums.length <= 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[1], dp[0]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[nums.length - 1];
    }


    /**
     * 714. 买卖股票的最佳时机含手续费
     *
     * @param prices
     * @param fee
     * @return
     */
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 == -1) {
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            // 针对当天无股票持有的情况
            // 1.昨天无持有
            // 2.昨天有持有，今天卖出
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i] - fee);
            // 针对当天有股票持有的情况
            // 1.昨天持有
            // 2.昨天无持有
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[n - 1][0];
    }

    /**
     * 309. 最佳买卖股票时机含冷冻期
     *
     * @param prices
     * @return
     */
    public int maxProfit5(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 == -1) {
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            if (i - 2 == -1) {
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
                dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
            }
            // 针对当天无股票持有的情况
            // 1.昨天无持有
            // 2.昨天有持有，今天卖出
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 针对当天有股票持有的情况
            // 1.昨天持有
            // 2.昨天无持有
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0] - prices[i]);
        }
        return dp[n - 1][0];
    }

    /**
     * 188. 买卖股票的最佳时机 IV
     *
     * @param k
     * @param prices
     * @return
     */
    public int maxProfit(int k, int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int n = prices.length;
        int[][][] dp = new int[n][k + 1][2];
        for (int i = 0; i < n; i++) {
            for (int j = k; j > 0; j--) {
                if (i - 1 == -1) {
                    dp[i][j][0] = 0;
                    dp[i][j][1] = -prices[i];
                    continue;
                }
                // 针对当天无股票持有的情况
                // 1.昨天无持有，截止昨天最大交易次数限制为k，今天选择rest，最大交易次数依然为k
                // 2.昨天有持有，今天卖出
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
                // 针对当天有股票持有的情况
                // 1.昨天持有
                // 2.昨天无持有
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
            }
        }
        return dp[n - 1][k][0];
    }

    /**
     * 123. 买卖股票的最佳时机 III
     *
     * @param prices
     * @return
     */
    public int maxProfit3(int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n][3][2];
        for (int i = 0; i < n; i++) {
            for (int k = 2; k > 0; k--) {
                if (i - 1 == -1) {
                    dp[i][k][0] = 0;
                    dp[i][k][1] = -prices[i];
                    continue;
                }
                // 针对当天无股票持有的情况
                // 1.昨天无持有，截止昨天最大交易次数限制为k，今天选择rest，最大交易次数依然为k
                // 2.昨天有持有，今天卖出
                dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k][1] + prices[i]);
                // 针对当天有股票持有的情况
                // 1.昨天持有
                // 2.昨天无持有
                dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i - 1][k - 1][0] - prices[i]);
            }
        }
        return dp[n - 1][2][0];
    }

    /**
     * 122. 买卖股票的最佳时机 II
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 == -1) {
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            // 针对当天无股票持有的情况
            // 1.昨天无持有
            // 2.昨天有持有，今天卖出
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 针对当天有股票持有的情况
            // 1.昨天持有
            // 2.昨天无持有
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[n - 1][0];
    }

    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 < 0) {
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            // 针对当天无股票持有的情况
            // 1.昨天无持有
            // 2.昨天有持有，今天卖出
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 针对当天有股票持有的情况
            // 1.昨天持有
            // 2.昨天无持有
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[n - 1][0];
    }
}
