package com.zxw.datastruct;

/**
 * @author zxw
 * @date 2020-05-11 10:10:23
 * @Description
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
