package com.zxw.leetcode.topic;

import cn.hutool.core.lang.Assert;

/**
 * @author zxw
 * @date 2022/6/29 14:25
 */
public class SearchAlgor {

    public static void main(String[] args) {
        SearchAlgor searchAlgor = new SearchAlgor();
        Assert.isTrue(0 == searchAlgor.search(new int[]{4,5,6,7,0,1,2}, 0));
    }

    public int search(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int lo = 0;
        int hi = nums.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (target == nums[mid]) {
                return mid;
            }
            // 是否为升序列
            if (nums[0] <= nums[mid]) {
                // 代表该值在左区间中
                if (target >= nums[0] && target < nums[mid]) {
                    hi = mid - 1;
                }else{
                    lo = mid + 1;
                }
            } else {
                // 如果是在右区间中
                if (target > nums[mid] && target <= nums[nums.length - 1]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }
        return -1;
    }
}
