package com.zxw.leetcode.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 贪心算法
 * 所谓贪心算法，就是指它的每一步计算作出的都是在当前看起来最好的选择，也就是说它所
 * 作出的选择只是在某种意义上的局部最优选择，并不从整体最优考虑。在这里，我把这两种
 * 选择的思路称作局部最优解和整体最优解。
 * <p>
 * 1. 根据问题来建立数学模型，一般面试题会定义一个简单模型；
 * 2. 把待求解问题划分成若干个子问题，对每个子问题进行求解，得到子问题的局部最优解；
 * 3. 把子问题的局部最优解进行合并，得到最后基于局部最优解的一个解，即原问题的答案。
 * <p>
 * 但是通过硬币找零问题，我们也发现了贪心算法本身的局限性：
 * 1. 不能保证求得的最后解是最佳的；
 * 2. 不能用来求最大或最小解问题；
 * 3. 只能求满足某些约束条件的可行解的范围。
 *
 * @author zxw
 * @date 2022/6/8 22:56
 */
@Slf4j
public class GreedyAlgorithm {

    public static void main(String[] args) {
        int[] values = {1,7,10}; // 硬币面值
        int total = 14; // 总价
        System.out.println(getMinCoinCountOfValueHelper(total, values)); // 输出结果
    }

    /**
     * @param total 金额
     * @param coins 币种数组，从大到小排序
     * @return 返回币数，如果返回-1表示无法凑够total
     */
    private static int getMinCoinCountOfValueHelper(int total, int[] coins) {
        if (coins.length == 0) {
            return -1;
        }//当前币值
        int currentCoin = coins[0];
        //使用当前币值数量
        int useCurrentCoinCount = total / currentCoin;
        int restTotal = total - useCurrentCoinCount * currentCoin;
        // 如果restTotal为0，表示余额已除尽，组合完成
        if (restTotal == 0) {
            return useCurrentCoinCount;
        }
        // 其他币种数量
        int coninCount = -1;
        // 剩余的币种
        int[] restCoins = Arrays.copyOfRange(coins, 1, coins.length);
        while (useCurrentCoinCount >= 0) {
            // 否则尝试用剩余面值求当前余额的硬币总数
            coninCount = getMinCoinCountOfValueHelper(restTotal, restCoins);
            // 如果后续没有有可用组合,退一步，当前useCurrentCoinCount币数减1
            if (coninCount == -1) {
                // 否则尝试把当前面值数-1
                useCurrentCoinCount--;
                // 重新计算restTotal
                restTotal = total - useCurrentCoinCount * currentCoin;
            } else {
                return useCurrentCoinCount + coninCount;
            }
        }
        return -1;
    }

    static int getMinCoinCountOfValue(int total, int[] values, int valueIndex) {
        int valueCount = values.length;
        int minResult = Integer.MAX_VALUE;
        int currentValue = values[valueIndex];
        int maxCount = total / currentValue;
        for (int count = maxCount; count >= 0; count--) {
            int rest = total - count * currentValue;
            // 如果rest为0，表示余额已除尽，组合完成
            if (rest == 0) {
                minResult = Math.min(minResult, count);
                break;
            }
            // 否则尝试用剩余面值求当前余额的硬币总数
            int restCount = getMinCoinCountOfValue(rest, values, valueIndex + 1);
            // 如果后续没有可用组合
            if (restCount == Integer.MAX_VALUE) {
                // 如果当前面值已经为0，返回-1表示尝试失败
                if (count == 0) {
                    break;
                }
                // 否则尝试把当前面值-1
                continue;
            }
            minResult = Math.min(minResult, count + restCount);
        }
        return minResult;
    }

    int getMinCoinCountLoop(int total, int[] values, int k) {
        int minCount = Integer.MAX_VALUE;
        int valueCount = values.length;

        if (k == valueCount) {
            return Math.min(minCount, getMinCoinCountOfValue(total, values, 0));
        }
        for (int i = k; i <= valueCount - 1; i++) {
            // k位置已经排列好
            int t = values[k];
            values[k] = values[i];
            values[i] = t;
            minCount = Math.min(minCount, getMinCoinCountLoop(total, values, k + 1)); // 考虑后一位
            // 回溯
            t = values[k];
            values[k] = values[i];
            values[i] = t;
        }
        return minCount;
    }

    int getMinCoinCountOfValue() {
        int[] values = {5, 3}; // 硬币面值
        int total = 11; // 总价
        int minCoin = getMinCoinCountLoop(total, values, 0);

        return (minCoin == Integer.MAX_VALUE) ? -1 : minCoin; // 输出答案
    }
}
