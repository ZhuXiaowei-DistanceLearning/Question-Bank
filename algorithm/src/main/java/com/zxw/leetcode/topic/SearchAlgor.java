package com.zxw.leetcode.topic;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zxw
 * @date 2022/6/29 14:25
 */
public class SearchAlgor {

    public static void main(String[] args) {
        SearchAlgor searchAlgor = new SearchAlgor();
        List<Integer> list = new ArrayList<>();
        list.stream().mapToInt(Integer::valueOf).toArray();
//        Assert.isTrue(0 == searchAlgor.search(new int[]{4,5,6,7,0,1,2}, 0));
        Assert.isTrue(ArrayUtil.equals(new int[]{1, 2}, searchAlgor.searchRange(new int[]{1, 2, 2}, 2)));
        Assert.isTrue(ArrayUtil.equals(new int[]{3, 4}, searchAlgor.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));
        Assert.isTrue(ArrayUtil.equals(new int[]{-1, -1}, searchAlgor.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6)));
        Assert.isTrue(ArrayUtil.equals(new int[]{0, 1}, searchAlgor.searchRange(new int[]{2, 2}, 2)));
    }

    public int[] searchRange(int[] nums, int target) {
        return new int[]{left_bound(nums, target), right_bound(nums, target)};
    }

    int left_bound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        // 搜索区间为 [left, right]
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                // 搜索区间变为 [mid+1, right]
                left = mid + 1;
            } else if (nums[mid] > target) {
                // 搜索区间变为 [left, mid-1]
                right = mid - 1;
            } else if (nums[mid] == target) {
                // 收缩右侧边界
                right = mid - 1;
            }
        }
        // 检查出界情况
        if (left >= nums.length || nums[left] != target) {
            return -1;
        }
        return left;
    }

    int right_bound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] == target) {
                // 这里改成收缩左侧边界即可
                left = mid + 1;
            }
        }
        // 这里改为检查 right 越界的情况，见下图
        if (right < 0 || nums[right] != target) {
            return -1;
        }
        return right;
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
                } else {
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
