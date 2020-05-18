package com.zxw.leetcode.swordoffer.data;

import java.util.Stack;

public class MinStack {
    Stack<Integer> s1 = new Stack<>();
    Stack<Integer> s2 = new Stack<>();

    /**
     * initialize your data structure here.
     */
    public MinStack() {

    }

    public void push(int x) {
        if (s1.isEmpty()) {
            s2.push(x);
        } else if (x < s2.peek()) {
            s2.push(x);
        } else {
            s2.push(s2.peek());
        }
        s1.push(x);
    }

    public void pop() {
        s1.pop();
        s2.pop();
    }

    public int top() {
        return s1.peek();
    }

    public int min() {
        return s2.peek();
    }
}