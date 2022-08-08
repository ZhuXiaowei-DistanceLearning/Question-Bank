package com.zxw.leetcode.topic;

import com.zxw.leetcode.type.tree.LeetCodeWrapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zxw
 * @date 2022/8/8 14:10
 */
public class Window {

    /**
     * 单调队列
     * @param args
     */
    public static void main(String[] args) {
        Window window = new Window();
        window.maxSlidingWindow(LeetCodeWrapper.stringToIntegerArray("[1,3,-1,-3,5,3,6,7]"),3);
    }

    /**
     * 239.滑动窗口
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 1) {
            return nums;
        }
        List<Integer> list = new ArrayList<Integer>();
        MonotonicQueue queue = new MonotonicQueue();
        for (int i = 0; i < nums.length; i++) {
            if (i < k - 1) {
                queue.push(nums[i]);
            } else {
                queue.push(nums[i]);
                list.add(queue.max());
                queue.pop(nums[i - k + 1]);
            }
        }
        return list.stream().mapToInt(Integer::valueOf).toArray();
    }

    class MonotonicQueue {
        private LinkedList<Integer> list = new LinkedList<>();

        public void push(int n) {
            while (!list.isEmpty() && list.getLast() < n) {
                list.pollLast();
            }
            list.addLast(n);
        }

        public int max() {
            return list.getFirst();
        }

        public void pop(int n) {
            if (n == list.getFirst()) {
                list.pollFirst();
            }
        }
    }
}
