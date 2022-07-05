package com.zxw.leetcode.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * https://labuladong.github.io/algo/4/29/105/
 * 1.元素无重不可复选
 * 2.元素可重不复选
 * 3.元素无重可复选
 * 子集（元素无重不可复选）：
 * 组合（元素无重不可复选）：大小为k的子集
 * 排列（元素无重不可复选）：
 * 子集/组合（元素可重不可复选）：排序
 * 排列（元素可重不可复选）：
 * 回溯算法最重要的就是终止条件
 *
 * @author zxw
 * @date 2022/6/28 11:33
 */
public class BackTrace {

    public static void main(String[] args) {
        BackTrace backTrace = new BackTrace();
        backTrace.permute(new int[]{1, 2, 3});
    }

    List<List<Integer>> res = new ArrayList();
    LinkedList<Integer> list = new LinkedList();


    /**
     * 39. 组合总和
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        backtrace2(candidates, target,0, 0);
        return res;
    }

    public void backtrace2(int[] candidates, int target, int start, int sum){
        if(target == sum){
            res.add(new LinkedList(list));
            return;
        }
        if(sum > target){
            return;
        }
        for(int i=start;i<candidates.length;i++){
            list.addLast(candidates[i]);
            int s = sum + candidates[i];
            backtrace2(candidates, target, i,s);
            list.removeLast();
        }
    }

    /**
     * 47. 全排列 II
     * @param nums
     * @return
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        if(nums.length == 1){
            res.add(List.of(nums[0]));
            return res;
        }
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        backtrace(nums, used);
        return res;
    }

    public void backtrace(int[] nums, boolean[] used){
        if(list.size() == nums.length){
            res.add(new LinkedList(list));
            return;
        }
        for(int i = 0; i<nums.length;i++){
            if(used[i]){
                continue;
            }
            if(i > 0 && nums[i] == nums[i-1] && !used[i - 1]){
                continue;
            }
            list.addLast(nums[i]);
            used[i] = true;
            backtrace(nums, used);
            list.removeLast();
            used[i] = false;
        }
    }

    /**
     * 40. 组合总和 II
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if (candidates.length == 0) {
            return res;
        }
        Arrays.sort(candidates);
        backtrace(candidates, target, 0, 0);
        return res;
    }

    public void backtrace(int[] candidates, int target, int sum, int start) {
        if (sum == target) {
            res.add(new LinkedList<>(list));
            return;
        }
        if(sum > target){
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if(i > start && candidates[i] == candidates[i-1]){
                continue;
            }
            list.addLast(candidates[i]);
            int s = sum + candidates[i];
            backtrace(candidates, target, s, i + 1);
            list.removeLast();
        }
    }

    /**
     * 90. 子集 II
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        if (nums.length == 0) {
            return res;
        }
        Arrays.sort(nums);
        backtrace2(nums, 0);
        return res;
    }

    public void backtrace2(int[] nums, int start) {
        res.add(new LinkedList<>(list));

        for (int i = 0; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            list.addLast(nums[i]);
            backtrace2(nums, i + 1);
            list.removeLast();
        }
    }

    /**
     * 46. 全排列
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        if (nums.length == 0) {
            return res;
        }
        backtrace(nums);
        return res;
    }

    public void backtrace(int[] nums) {
        if (list.size() == nums.length) {
            res.add(new LinkedList(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (list.contains(nums[i])) {
                continue;
            }
            list.addLast(nums[i]);
            backtrace(nums);
            list.removeLast();
        }
    }

    /**
     * 78.子集
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        if (nums.length == 0) {
            return res;
        }
        backtrace(nums, 0);
        return res;
    }

    public void backtrace(int[] nums, int start) {
        res.add(new LinkedList(list));
        for (int i = start; i < nums.length; i++) {
            list.addLast(nums[i]);
            backtrace(nums, i + 1);
            list.removeLast();
        }
    }

    /**
     * 77.组合
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine(int n, int k) {
        if (n == 0 || k == 0) {
            return res;
        }
        backtrace(n, k, 1);
        return res;
    }

    public void backtrace(int n, int k, int start) {
        if (list.size() == k) {
            res.add(new LinkedList(list));
            return;
        }
        for (int i = start; i <= n; i++) {
            list.addLast(i);
            backtrace(n, k, i + 1);
            list.removeLast();
        }
    }


}
