package com.zxw.leetcode.swordoffer.data;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 面试题59 - II. 队列的最大值
 * 请定义一个队列并实现函数 max_value 得到队列里的最大值，要求函数max_value、push_back 和 pop_front 的均摊时间复杂度都是O(1)。
 * 若队列为空，pop_front 和 max_value 需要返回 -1
 */
public class MaxQueue {

    LinkedList<Integer> l1 = new LinkedList();
    LinkedList<Integer> l2 = new LinkedList();

    public MaxQueue() {

    }

    public int max_value() {
        return l2.size() == 0 ? -1 : l2.peek();
    }

    public void push_back(int value) {
        l1.addLast(value);
        if (l2.size() == 0) {
            l2.push(value);
        } else {
            if (l1.peek() < value) {
                l1.addLast(value);
            } else {
                l1.addFirst(value);
            }
        }
    }

    public int pop_front() {
        if (l1.size() == 0) {
            return -1;
        }
        Integer res = l1.removeFirst();
        if (res.equals(l2.peek())) {
            l2.removeFirst();
        }else{
            l2.removeLast();
        }
        return res;
    }
}