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
        Assert.isTrue(5 == dp.maxProfit(LeetCodeWrapper.stringToIntegerArray("[7,1,5,3,6,4]")));
    }

    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            if (i - 1 < 0) {
                dp[i][0] = 0;
                dp[i][1] = Integer.MIN_VALUE;
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
}
