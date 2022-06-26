package com.zxw.leetcode.algorithm;

import java.util.*;

/**
 * @author zxw
 * @date 2022/6/26 22:17
 */
public class TwoPoint {

    public static void main(String[] args) {
        TwoPoint twoPoint = new TwoPoint();
        twoPoint.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1});
        twoPoint.fourSum(new int[]{1000000000, 1000000000, 1000000000, 1000000000}, -294967296);
        twoPoint.longestPalindrome("babad");
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int res = 0;
        int l = 0;
        int r = 0;
        Map<Character, Integer> map = new HashMap();
        char[] c = s.toCharArray();
        while (r < c.length) {
            if (map.containsKey(c[r])) {
                l = Math.max(l, map.get(c[r]) + 1);
            }
            res = Math.max(res, r - l + 1);
            map.put(c[r], r);
            r++;
        }
        return res;
    }

    public String longestPalindrome(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            String s1 = palindrome(s, i, i);
            String s2 = palindrome(s, i, i + 1);
            res = res.length() > s1.length() ? res : s1;
            res = res.length() > s2.length() ? res : s2;
        }
        return res;
    }

    /**
     * 本地的核心在于找到左边最高的位置与右边最高的位置，减去自身的位置就能得到当前位置所能装的雨水
     *
     * @param s
     * @param l
     * @param r
     * @return
     */
    public String palindrome(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        return s.substring(l + 1, r);
    }

    int trap(int[] height) {
        int n = height.length;
        int res = 0;
        for (int i = 1; i < n - 1; i++) {
            int l_max = 0, r_max = 0;
            // 找右边最高的柱子
            for (int j = i; j < n; j++)
                r_max = Math.max(r_max, height[j]);
            // 找左边最高的柱子
            for (int j = i; j >= 0; j--)
                l_max = Math.max(l_max, height[j]);
            // 如果自己就是最高的话，
            // l_max == r_max == height[i]
            res += Math.min(l_max, r_max) - height[i];
        }
        return res;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums); // 排序
        List<List<Integer>> res = new ArrayList();
        for (int i = 0; i < nums.length; i++) {
            List<List<Integer>> ts = threeSum(nums, i + 1, target - nums[i]);
            for (int j = 0; j < ts.size(); j++) {
                List<Integer> t = ts.get(j);
                t.add(0, nums[i]);
                res.add(t);
            }
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) i++;
        }
        return res;
    }

    public List<List<Integer>> threeSum(int[] nums, int start, long target) {
        List<List<Integer>> res = new ArrayList();
        for (int i = start; i < nums.length; i++) {
            List<List<Integer>> ts = twoSum(nums, i + 1, target - nums[i]);
            for (int j = 0; j < ts.size(); j++) {
                List<Integer> t = ts.get(j);
                t.add(0, nums[i]);
                res.add(t);
            }
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) i++;
        }
        return res;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums); // 排序
        List<List<Integer>> res = new ArrayList();
        for (int i = 0; i < nums.length; i++) {
            List<List<Integer>> ts = twoSum(nums, i + 1, 0 - nums[i]);
            for (int j = 0; j < ts.size() - 1; j++) {
                List<Integer> t = ts.get(j);
                t.add(0, nums[i]);
                res.add(t);
            }
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) i++;
        }
        return res;
    }

    public List<List<Integer>> twoSum(int[] nums, int start, long target) {
        int l = start;
        int r = nums.length - 1;
        List<List<Integer>> res = new ArrayList();
        while (l < r) {
            int left = nums[l];
            int right = nums[r];
            int sum = left + right;
            if (sum < target) {
                while (l < r && nums[l] == left) {
                    l++;
                }
            } else if (sum > target) {
                while (l < r && nums[r] == right) {
                    r--;
                }
            } else {
                while (l < r && nums[l] == left) {
                    l++;
                }
                while (l < r && nums[r] == right) {
                    r--;
                }
                List<Integer> list = new ArrayList();
                list.add(left);
                list.add(right);
                res.add(list);
            }
        }
        return res;
    }

    public List<List<Integer>> nSum(int[] nums, int n, int start, long target) {
        List<List<Integer>> res = new ArrayList();
        if (n == 2) {
            int l = start;
            int r = nums.length - 1;
            while (l < r) {
                int left = nums[l];
                int right = nums[r];
                int sum = left + right;
                if (sum < target) {
                    while (l < r && nums[l] == left) {
                        l++;
                    }
                } else if (sum > target) {
                    while (l < r && nums[r] == right) {
                        r--;
                    }
                } else {
                    while (l < r && nums[l] == left) {
                        l++;
                    }
                    while (l < r && nums[r] == right) {
                        r--;
                    }
                    List<Integer> list = new ArrayList();
                    list.add(left);
                    list.add(right);
                    res.add(list);
                }
            }
        } else {
            for (int i = start; i < nums.length; i++) {
                List<List<Integer>> ts = nSum(nums, n - 1, i + 1, (long) target - (long) nums[i]);
                for (int j = 0; j < ts.size(); j++) {
                    List<Integer> t = ts.get(j);
                    t.add(0, nums[i]);
                    res.add(t);
                }
                while (i < nums.length - 1 && nums[i] == nums[i + 1]) i++;
            }
        }
        return res;
    }
}
