package com.zxw.leetcode.swordoffer.data;

import java.util.Stack;

/**
 * 面试题09. 用两个栈实现队列
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
 */
class CQueue {

    Stack<Integer> stack = new Stack<>();
    Stack<Integer> stack2 = new Stack<>();

    public CQueue() {

    }

    public void appendTail(int value) {
        while (!stack.isEmpty()) {
            stack2.push(stack.pop());
        }
        stack.push(value);
        while (!stack2.isEmpty()) {
            stack.push(stack2.pop());
        }
    }

    public int deleteHead() {
        return stack.isEmpty() ? -1 : stack.pop();
    }
}