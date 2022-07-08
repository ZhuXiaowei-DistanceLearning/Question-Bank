package com.zxw.leetcode.topic;

import cn.hutool.core.lang.Assert;
import com.zxw.leetcode.type.tree.LeetCodeWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zxw
 * @date 2022/7/6 9:43
 */
public class TwoArray {

    public static void main(String[] args) {
        TwoArray twoArray = new TwoArray();
        Assert.isTrue(twoArray.minPathSum(LeetCodeWrapper.stringToInt2dArray("[[1,3,1],[1,5,1],[4,2,1]]")) == 7);
        twoArray.groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
    }

    /**
     * 64.最小路径和
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = grid[i][0] + dp[i - 1][0];
        }
        for (int i = 1; i < n; i++) {
            dp[0][i] = grid[0][i] + dp[0][i - 1];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 62.不同路径
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        if (m <= 1 || n <= 1) {
            return 1;
        }
        int[][] dp = new int[m][n];
        dp[0][1] = 1;
        dp[1][0] = 1;
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        if (strs.length <= 1) {
            res.add(List.of(strs[0]));
            return res;
        }
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            String s = encode(strs[i]);
            List<String> list = map.computeIfAbsent(s, e -> new ArrayList<>());
            list.add(strs[i]);
        }
        map.forEach((k, v) -> {
            res.add(v);
        });
        return res;
    }

    public String encode(String s) {
        char[] c = new char[26];
        for (int i = 0; i < s.length(); i++) {
            c[s.charAt(i) - 'a']++;
        }
        return new String(c);
    }

    /**
     * 48.旋转图像
     *
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        if (matrix.length == 0) {
            return;
        }
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // 然后反转二维矩阵的每一行
        for (int[] row : matrix) {
            reverse(row);
        }
    }

    // 反转一维数组
    void reverse(int[] arr) {
        int i = 0, j = arr.length - 1;
        while (j > i) {
            // swap(arr[i], arr[j]);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
    }
}
