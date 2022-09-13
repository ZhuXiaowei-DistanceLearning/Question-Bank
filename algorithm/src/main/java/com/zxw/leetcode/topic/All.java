package com.zxw.leetcode.topic;

import cn.hutool.core.lang.Assert;
import com.zxw.leetcode.type.tree.LeetCodeWrapper;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zxw
 * @date 2022/9/13 11:53
 */
public class All {
    public static void main(String[] args) {
        All all = new All();
        Assert.isTrue(5 == all.leastInterval(LeetCodeWrapper.stringToCharArray("[\"A\",\"A\",\"A\"]"), 1));
        Assert.isTrue(16 == all.leastInterval(LeetCodeWrapper.stringToCharArray("[\"A\",\"A\",\"A\",\"A\",\"A\",\"A\",\"B\",\"C\",\"D\",\"E\",\"F\",\"G\"]"), 2));
        Assert.isTrue(8 == all.leastInterval(LeetCodeWrapper.stringToCharArray("\n" +
                "[\"A\",\"A\",\"A\",\"B\",\"B\",\"B\"]"), 2));
    }

    /**
     * 621. 任务调度器
     *
     * @param tasks
     * @param n
     * @return
     */
    public int leastInterval(char[] tasks, int n) {
        if (n == 0) {
            return tasks.length;
        }
        AtomicInteger max = new AtomicInteger();
        HashMap<Character, Integer> map = new HashMap();
        for (char c : tasks) {
            int num = map.getOrDefault(c, 0) + 1;
            max.set(Math.max(max.get(), num));
            map.put(c, num);
        }
        AtomicInteger maxCount = new AtomicInteger();
        map.forEach((k, v) -> {
            if (max.get() - v == 0) {
                maxCount.incrementAndGet();
            }
        });
        return Math.max((max.get() - 1) * (n + 1) + maxCount.get(), tasks.length);
    }
}
