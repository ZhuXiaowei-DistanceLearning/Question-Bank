package com.zxw.leetcode.topic;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author zxw
 * @date 2022/7/7 17:43
 */
public class Region {
    public static void main(String[] args) {
        Region region = new Region();
        region.merge(new int[][]{{1, 3}});
    }

    public int[][] merge(int[][] intervals) {
        if(intervals.length <= 1){
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparingInt(v -> v[0]));
        // 遍历区间
        int[][] res = new int[intervals.length][2];
        int idx = -1;
        int l = intervals[0][0];
        int r = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] > r) {
                ++idx;
                res[idx][0] = l;
                res[idx][1] = r;
                r = intervals[i][1];
                l = intervals[i][0];
            } else {
                r = Math.max(r, intervals[i][1]);
            }
            if (i == intervals.length - 1) {
                ++idx;
                res[idx][0] = l;
                res[idx][1] = r;
            }
        }
        return Arrays.copyOf(res, idx + 1);
    }
}
