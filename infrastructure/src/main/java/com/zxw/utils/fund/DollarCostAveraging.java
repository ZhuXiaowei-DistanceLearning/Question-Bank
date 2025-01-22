package com.zxw.utils.fund;

import java.util.ArrayList;
import java.util.List;

public class DollarCostAveraging {
    public static void main(String[] args) {
        // 示例数据
        String[] dates = {"2023-11-29","2023-11-30","2023-12-01","2023-12-04","2023-12-05","2023-12-06","2023-12-07","2023-12-08","2023-12-11","2023-12-12","2023-12-13","2023-12-14","2023-12-15","2023-12-18","2023-12-19","2023-12-20","2023-12-21","2023-12-22","2023-12-25","2023-12-26","2023-12-27","2023-12-28","2023-12-29","2023-12-31","2024-01-02","2024-01-03","2024-01-04","2024-01-05","2024-01-08","2024-01-09","2024-01-10","2024-01-11","2024-01-12","2024-01-15","2024-01-16","2024-01-17","2024-01-18","2024-01-19","2024-01-22","2024-01-23","2024-01-24","2024-01-25","2024-01-26","2024-01-29","2024-01-30","2024-01-31","2024-02-01","2024-02-02","2024-02-05","2024-02-06","2024-02-07","2024-02-08","2024-02-19","2024-02-20","2024-02-21","2024-02-22","2024-02-23","2024-02-26","2024-02-27","2024-02-28","2024-02-29","2024-03-01","2024-03-04","2024-03-05","2024-03-06","2024-03-07","2024-03-08","2024-03-11","2024-03-12","2024-03-13","2024-03-14","2024-03-15","2024-03-18","2024-03-19","2024-03-20","2024-03-21","2024-03-22","2024-03-25","2024-03-26","2024-03-27","2024-03-28","2024-03-29","2024-04-01","2024-04-02","2024-04-03","2024-04-08","2024-04-09","2024-04-10","2024-04-11","2024-04-12","2024-04-15","2024-04-16","2024-04-17","2024-04-18","2024-04-19","2024-04-22","2024-04-23","2024-04-24","2024-04-25","2024-04-26","2024-04-29","2024-04-30","2024-05-06","2024-05-07","2024-05-08","2024-05-09","2024-05-10","2024-05-13","2024-05-14","2024-05-15","2024-05-16","2024-05-17","2024-05-20","2024-05-21","2024-05-22","2024-05-23","2024-05-24","2024-05-27","2024-05-28","2024-05-29","2024-05-30","2024-05-31","2024-06-03","2024-06-04","2024-06-05","2024-06-06","2024-06-07","2024-06-11","2024-06-12","2024-06-13","2024-06-14","2024-06-17","2024-06-18","2024-06-19","2024-06-20","2024-06-21","2024-06-24","2024-06-25","2024-06-26","2024-06-27","2024-06-28","2024-06-30","2024-07-01","2024-07-02","2024-07-03","2024-07-04","2024-07-05","2024-07-08","2024-07-09","2024-07-10","2024-07-11","2024-07-12","2024-07-15","2024-07-16","2024-07-17","2024-07-18","2024-07-19","2024-07-22","2024-07-23","2024-07-24","2024-07-25","2024-07-26","2024-07-29","2024-07-30","2024-07-31","2024-08-01","2024-08-02","2024-08-05","2024-08-06","2024-08-07","2024-08-08","2024-08-09","2024-08-12","2024-08-13","2024-08-14","2024-08-15","2024-08-16","2024-08-19","2024-08-20","2024-08-21","2024-08-22","2024-08-23","2024-08-26","2024-08-27","2024-08-28","2024-08-29","2024-08-30","2024-09-02","2024-09-03","2024-09-04","2024-09-05","2024-09-06","2024-09-09","2024-09-10","2024-09-11","2024-09-12","2024-09-13","2024-09-18","2024-09-19","2024-09-20","2023-06-02","2023-06-05","2023-06-06","2023-06-07","2023-06-08","2023-06-09","2023-06-12","2023-06-13","2023-06-14","2023-06-15","2023-06-16","2023-06-19","2023-06-20","2023-06-21","2023-06-26","2023-06-27","2023-06-28","2023-06-29","2023-06-30","2023-07-03","2023-07-04","2023-07-05","2023-07-06","2023-07-07","2023-07-10","2023-07-11","2023-07-12","2023-07-13","2023-07-14","2023-07-17","2023-07-18","2023-07-19","2023-07-20","2023-07-21","2023-07-24","2023-07-25","2023-07-26","2023-07-27","2023-07-28","2023-07-31","2023-08-01","2023-08-02","2023-08-03","2023-08-04","2023-08-07","2023-08-08","2023-08-09","2023-08-10","2023-08-11","2023-08-14","2023-08-15","2023-08-16","2023-08-17","2023-08-18","2023-08-21","2023-08-22","2023-08-23","2023-08-24","2023-08-25","2023-08-28","2023-08-29","2023-08-30","2023-08-31","2023-09-01","2023-09-04","2023-09-05","2023-09-06","2023-09-07","2023-09-08","2023-09-11","2023-09-12","2023-09-13","2023-09-14","2023-09-15","2023-09-18","2023-09-19","2023-09-20","2023-09-21","2023-09-22","2023-09-25","2023-09-26","2023-09-27","2023-09-28","2023-10-09","2023-10-10","2023-10-11","2023-10-12","2023-10-13","2023-10-16","2023-10-17","2023-10-18","2023-10-19","2023-10-20","2023-10-23","2023-10-24","2023-10-25","2023-10-26","2023-10-27","2023-10-30","2023-10-31","2023-11-01","2023-11-02","2023-11-03","2023-11-06","2023-11-07","2023-11-08","2023-11-09","2023-11-10","2023-11-13","2023-11-14","2023-11-15","2023-11-16","2023-11-17","2023-11-20","2023-11-21","2023-11-22","2023-11-23","2023-11-24","2023-11-27","2023-11-28","2023-11-29","2023-11-30","2023-12-01","2023-12-04","2023-12-05","2023-12-06","2023-12-07","2023-12-08","2023-12-11","2023-12-12","2023-12-13","2023-12-14","2023-12-15","2023-12-18","2023-12-19","2023-12-20","2023-12-21","2023-12-22","2023-12-25","2023-12-26","2023-12-27","2023-12-28","2023-12-29","2023-12-31","2024-01-02","2024-01-03","2024-01-04","2024-01-05","2024-01-08","2024-01-09","2024-01-10","2024-01-11","2024-01-12","2024-01-15","2024-01-16","2024-01-17","2024-01-18","2024-01-19","2024-01-22","2024-01-23","2024-01-24","2024-01-25","2024-01-26","2024-01-29","2024-01-30","2024-01-31","2024-02-01","2024-02-02","2024-02-05","2024-02-06","2024-02-07","2024-02-08","2024-02-19","2024-02-20","2024-02-21","2024-02-22","2024-02-23","2024-02-26","2024-02-27","2024-02-28","2024-02-29","2024-03-01","2024-03-04","2024-03-05","2024-03-06","2024-03-07","2024-03-08","2024-03-11","2024-03-12","2024-03-13","2024-03-14","2024-03-15","2024-03-18","2024-03-19","2024-03-20","2024-03-21","2024-03-22","2024-03-25","2024-03-26","2024-03-27","2024-03-28","2024-03-29","2024-04-01","2024-04-02","2024-04-03","2024-04-08","2024-04-09","2024-04-10","2024-04-11","2024-04-12","2024-04-15","2024-04-16","2024-04-17","2024-04-18","2024-04-19","2024-04-22","2024-04-23","2024-04-24","2024-04-25","2024-04-26","2024-04-29","2024-04-30","2024-05-06","2024-05-07","2024-05-08","2024-05-09","2024-05-10","2024-05-13","2024-05-14","2024-05-15","2024-05-16","2024-05-17","2024-05-20","2024-05-21","2024-05-22","2024-05-23","2024-05-24","2024-05-27","2024-05-28","2024-05-29","2024-05-30","2024-05-31","2024-06-03","2024-06-04","2024-06-05","2024-06-06","2024-06-07","2024-06-11","2024-06-12","2024-06-13","2024-06-14","2024-06-17","2024-06-18","2024-06-19","2024-06-20","2024-06-21","2024-06-24","2024-06-25","2024-06-26","2024-06-27","2024-06-28","2024-06-30","2024-07-01","2024-07-02","2024-07-03","2024-07-04","2024-07-05","2024-07-08","2024-07-09","2024-07-10","2024-07-11","2024-07-12","2024-07-15","2024-07-16","2024-07-17","2024-07-18","2024-07-19","2024-07-22","2024-07-23","2024-07-24","2024-07-25","2024-07-26","2024-07-29","2024-07-30","2024-07-31","2024-08-01","2024-08-02","2024-08-05","2024-08-06","2024-08-07","2024-08-08","2024-08-09","2024-08-12","2024-08-13","2024-08-14","2024-08-15","2024-08-16","2024-08-19","2024-08-20","2024-08-21","2024-08-22","2024-08-23","2024-08-26","2024-08-27","2024-08-28","2024-08-29","2024-08-30","2024-09-02","2024-09-03","2024-09-04","2024-09-05","2024-09-06","2024-09-09","2024-09-10","2024-09-11","2024-09-12","2024-09-13","2024-09-18","2024-09-19","2024-09-20","2024-09-23","2024-09-24","2024-09-25","2024-09-26","2024-09-27","2024-09-30","2024-10-08","2024-10-09","2024-10-10","2024-10-11","2024-10-14","2024-10-15","2024-10-16","2024-10-17","2024-10-18","2024-10-21","2024-10-22","2024-10-23","2024-10-24","2024-10-25","2024-10-28","2024-10-29","2024-10-30","2024-10-31","2024-11-01","2024-11-04","2024-11-05","2024-11-06","2024-11-07","2024-11-08","2024-11-11","2024-11-12","2024-11-13","2024-11-14","2024-11-15","2024-11-18","2024-11-19","2024-11-20","2024-11-21","2024-11-22","2024-11-25","2024-11-26","2024-11-27","2024-11-28","2024-11-29","2024-12-02","2024-12-03","2024-12-04","2024-12-05","2024-12-06","2024-12-09","2024-12-10","2024-12-11","2024-12-12","2024-12-13","2024-12-16","2024-12-17","2024-12-18","2024-12-19","2024-12-20","2024-12-23","2024-12-24","2024-12-25","2024-12-26","2024-12-27","2024-12-30","2024-12-31","2025-01-02","2025-01-03","2025-01-06","2025-01-07","2025-01-08","2025-01-09","2025-01-10","2025-01-13","2025-01-14","2025-01-15","2025-01-16","2025-01-17","2025-01-20"
        };

        double[] prices = {
                1.1152,1.1204,1.1287,1.1217,1.1226,1.1198,1.1281,1.1331,1.1405,1.1444,1.1604,1.1566,1.1517,1.1605,1.1692,1.1513,1.1640,1.1656,1.1644,1.1705,1.1718,1.1663,1.1617,1.1617,1.1609,1.1542,1.1513,1.1550,1.1697,1.1680,1.1764,1.1742,1.1752,1.1767,1.1738,1.1696,1.1793,1.1924,1.1952,1.1948,1.1965,1.2017,1.2036,1.2121,1.2116,1.1939,1.2084,1.2202,1.2192,1.2212,1.2305,1.2319,1.2342,1.2272,1.2271,1.2523,1.2539,1.2493,1.2514,1.2496,1.2550,1.2653,1.2638,1.2516,1.2578,1.2701,1.2607,1.2581,1.2703,1.2709,1.2676,1.2604,1.2681,1.2751,1.2860,1.2899,1.2928,1.2856,1.2842,1.2964,1.2973,1.2971,1.2954,1.2873,1.2888,1.2863,1.2882,1.2765,1.2861,1.2686,1.2541,1.2518,1.2448,1.2424,1.2324,1.2430,1.2576,1.2579,1.2526,1.2645,1.2684,1.2487,1.2783,1.2799,1.2816,1.2878,1.2899,1.2910,1.2973,1.3094,1.3058,1.3076,1.3100,1.3142,1.3105,1.3015,1.3097,1.3092,1.3099,1.3007,1.2930,1.3021,1.3037,1.3052,1.3197,1.3190,1.3167,1.3251,1.3351,1.3372,1.3371,1.3464,1.3491,1.3488,1.3460,1.3432,1.3382,1.3423,1.3436,1.3440,1.3368,1.3368,1.3395,1.3472,1.3530,1.3522,1.3588,1.3599,1.3605,1.3744,1.3617,1.3666,1.3705,1.3787,1.3606,1.3489,1.3409,1.3551,1.3526,1.3229,1.3080,1.3253,1.3270,1.3202,1.3346,1.3204,1.2926,1.2445,1.2579,1.2561,1.2797,1.2877,1.2883,1.3079,1.3082,1.3305,1.3355,1.3423,1.3400,1.3444,1.3324,1.3474,1.3403,1.3438,1.3357,1.3318,1.3406,1.3442,1.3187,1.3159,1.3100,1.2861,1.3045,1.3112,1.3234,1.3344,1.3365,1.3335,1.3515,1.3456,1.0503,1.0542,1.0566,1.0537,1.0604,1.0607,1.0725,1.0805,1.0828,1.0943,1.0865,1.0908,1.0880,1.0851,1.0818,1.0900,1.0931,1.0981,1.1138,1.1137,1.1077,1.1102,1.1026,1.0990,1.0999,1.1030,1.1087,1.1133,1.1088,1.1178,1.1256,1.1344,1.1229,1.1217,1.1294,1.1238,1.1243,1.1159,1.1298,1.1292,1.1289,1.1165,1.1146,1.1079,1.1188,1.1174,1.1079,1.1098,1.1124,1.1209,1.1132,1.1059,1.1005,1.0975,1.1077,1.1021,1.1136,1.0980,1.1060,1.1133,1.1279,1.1323,1.1304,1.1282,1.1293,1.1289,1.1229,1.1217,1.1259,1.1251,1.1196,1.1190,1.1272,1.1128,1.1168,1.1151,1.1056,1.0890,1.0861,1.0916,1.0766,1.0762,1.0817,1.0891,1.0944,1.0993,1.0929,1.0887,1.1002,1.1005,1.0859,1.0780,1.0653,1.0635,1.0692,1.0558,1.0444,1.0394,1.0514,1.0578,1.0685,1.0876,1.0967,1.0937,1.0971,1.0974,1.0900,1.1073,1.1066,1.1267,1.1207,1.1245,1.1244,1.1224,1.1135,1.1196,1.1183,1.1215,1.1192,1.1203,1.1152,1.1204,1.1287,1.1217,1.1226,1.1198,1.1281,1.1331,1.1405,1.1444,1.1604,1.1566,1.1517,1.1605,1.1692,1.1513,1.1640,1.1656,1.1644,1.1705,1.1718,1.1663,1.1617,1.1617,1.1609,1.1542,1.1513,1.1550,1.1697,1.1680,1.1764,1.1742,1.1752,1.1767,1.1738,1.1696,1.1793,1.1924,1.1952,1.1948,1.1965,1.2017,1.2036,1.2121,1.2116,1.1939,1.2084,1.2202,1.2192,1.2212,1.2305,1.2319,1.2342,1.2272,1.2271,1.2523,1.2539,1.2493,1.2514,1.2496,1.2550,1.2653,1.2638,1.2516,1.2578,1.2701,1.2607,1.2581,1.2703,1.2709,1.2676,1.2604,1.2681,1.2751,1.2860,1.2899,1.2928,1.2856,1.2842,1.2964,1.2973,1.2971,1.2954,1.2873,1.2888,1.2863,1.2882,1.2765,1.2861,1.2686,1.2541,1.2518,1.2448,1.2424,1.2324,1.2430,1.2576,1.2579,1.2526,1.2645,1.2684,1.2487,1.2783,1.2799,1.2816,1.2878,1.2899,1.2910,1.2973,1.3094,1.3058,1.3076,1.3100,1.3142,1.3105,1.3015,1.3097,1.3092,1.3099,1.3007,1.2930,1.3021,1.3037,1.3052,1.3197,1.3190,1.3167,1.3251,1.3351,1.3372,1.3371,1.3464,1.3491,1.3488,1.3460,1.3432,1.3382,1.3423,1.3436,1.3440,1.3368,1.3368,1.3395,1.3472,1.3530,1.3522,1.3588,1.3599,1.3605,1.3744,1.3617,1.3666,1.3705,1.3787,1.3606,1.3489,1.3409,1.3551,1.3526,1.3229,1.3080,1.3253,1.3270,1.3202,1.3346,1.3204,1.2926,1.2445,1.2579,1.2561,1.2797,1.2877,1.2883,1.3079,1.3082,1.3305,1.3355,1.3423,1.3400,1.3444,1.3324,1.3474,1.3403,1.3438,1.3357,1.3318,1.3406,1.3442,1.3187,1.3159,1.3100,1.2861,1.3045,1.3112,1.3234,1.3344,1.3365,1.3335,1.3515,1.3456,1.3500,1.3494,1.3438,1.3490,1.3459,1.3512,1.3551,1.3671,1.3661,1.3730,1.3852,1.3820,1.3876,1.3884,1.3899,1.3884,1.3888,1.3774,1.3774,1.3789,1.3829,1.3871,1.3797,1.3540,1.3597,1.3514,1.3682,1.4102,1.4210,1.4241,1.4312,1.4354,1.4339,1.4274,1.4083,1.4159,1.4208,1.4206,1.4276,1.4331,1.4371,1.4465,1.4401,1.4400,1.4435,1.4540,1.4571,1.4636,1.4600,1.4618,1.4563,1.4470,1.4620,1.4537,1.4561,1.4622,1.4573,1.4167,1.4181,1.4327,1.4426,1.4575,1.4573,1.4569,1.4417,1.4269,1.4210,1.4177,1.4363,1.4474,1.4318,1.4344,1.4345,1.4137,1.4156,1.4168,1.4417,1.4386,1.4514,1.4489
        };
        double weeklyInvestment = 100;
        double transactionFee = 0.001; // 交易费率

        // 计算dp数组
        double[][] dp = calculateDP(prices, weeklyInvestment, transactionFee);

        // 回溯找到最佳买入卖出点
        List<int[]> transactions = findOptimalTransactions(dp, prices, weeklyInvestment, transactionFee);

        // 打印结果
        for (int[] transaction : transactions) {
            System.out.println("买入日期：" + dates[transaction[0]]);
            System.out.println("卖出日期：" + dates[transaction[1]]);
            System.out.println("利润：" + calculateProfit(prices, transaction[0], transaction[1], weeklyInvestment, transactionFee));
        }
    }

    // 计算dp数组
    private static double[][] calculateDP(double[] prices, double weeklyInvestment, double transactionFee) {
        int n = prices.length;
        double[][] dp = new double[n][n + 1]; // dp[i][j]表示在第i天持有j份股票时的最大利润

        // 初始化第一行
        dp[0][1] = -prices[0] - prices[0] * transactionFee; // 第一天买入一份，考虑买入费用
        for (int i = 2; i <= n; i++) {
            dp[0][i] = Double.NEGATIVE_INFINITY;
        }

        // 动态规划
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                // 如果是周三，则买入一份
                if ((i + 1) % 7 == 0) {
                    dp[i][j + 1] = Math.max(dp[i - 1][j + 1], dp[i - 1][j] - prices[i] - prices[i] * transactionFee);
                }
                // 卖出部分或全部股票
                for (int k = 0; k <= j; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][k] + (j - k) * prices[i] - (j - k) * prices[i] * transactionFee);
                }
            }
        }
        return dp;
    }

    // 回溯找到最佳买入卖出点
    private static List<int[]> findOptimalTransactions(double[][] dp, double[] prices, double weeklyInvestment, double transactionFee) {
        List<int[]> transactions = new ArrayList<>();
        int n = prices.length;
        int maxProfitIndex = 0;
        for (int i = 1; i <= n; i++) {
            if (dp[n - 1][i] > dp[n - 1][maxProfitIndex]) {
                maxProfitIndex = i;
            }
        }

        // 从dp数组的最后一个元素开始回溯
        int i = n - 1, j = maxProfitIndex;
        while (i >= 0 && j > 0) {
            if (dp[i][j] != dp[i - 1][j]) {
                // 发生了卖出操作
                transactions.add(0, new int[]{i, i}); // 买入和卖出日期相同
                j--;
            } else if (i > 0 && (i + 1) % 7 == 0 && dp[i][j] == dp[i - 1][j - 1] - prices[i] - prices[i] * transactionFee) {
                // 发生了买入操作
                transactions.add(0, new int[]{i, -1}); // 卖出日期未知，暂时标记为-1
                j--;
            }
            i--;
        }

        // 填充未卖出的股票的卖出日期
        for (int k = transactions.size() - 1; k >= 0; k--) {
            if (transactions.get(k)[1] == -1) {
                transactions.get(k)[1] = transactions.get(k + 1)[0] - 1;
            }
        }

        return transactions;
    }

    // 计算某次交易的利润
    private static double calculateProfit(double[] prices, int buyIndex, int sellIndex, double weeklyInvestment, double transactionFee) {
        // ... (省略，根据具体需求计算)
        return 0;
    }

}