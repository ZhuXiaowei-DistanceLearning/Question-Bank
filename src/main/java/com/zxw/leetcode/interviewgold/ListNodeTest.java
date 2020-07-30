package com.zxw.leetcode.interviewgold;

import com.zxw.common.datastruct.ListNode;

import java.util.HashSet;

/**
 * @author zxw
 * @date 2020/7/16 10:20
 */
public class ListNodeTest {
    public static void main(String[] args) {
        ListNodeTest listNodeTest = new ListNodeTest();
    }

    /**
     * 面试题 02.01. 移除重复节点
     * 编写代码，移除未排序链表中的重复节点。保留最开始出现的节点。
     * 示例1:
     * 输入：[1, 2, 3, 3, 2, 1]
     * 输出：[1, 2, 3]
     *
     * @param head
     * @return
     */
    public ListNode removeDuplicateNodes(ListNode head) {
        if (head == null) {
            return head;
        }
        HashSet set = new HashSet();
        ListNode res = head;
        while (head != null) {
            if (set.contains(head.val)) {
                head.next = head.next == null ? head.next.next : null;
            } else {
                set.add(head.val);
            }
            head = head.next;
        }
        return res;
    }

    /**
     * 面试题 02.02. 返回倒数第 k 个节点
     * 实现一种算法，找出单向链表中倒数第 k 个节点。返回该节点的值。
     * 注意：本题相对原题稍作改动
     * 示例：
     * 输入： 1->2->3->4->5 和 k = 2
     * 输出： 4
     *
     * @param head
     * @param k
     * @return
     */
    public int kthToLast(ListNode head, int k) {
        ListNode node = head;
        while (head != null) {
            if (k != 0) {
                k--;
            } else {
                node = node.next;
            }
            ListNode next = head.next;
            head = head.next;
        }
        return node.val;
    }
}
