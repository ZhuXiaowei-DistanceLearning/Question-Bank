package com.zxw.leetcode.topic;

import com.zxw.datastruct.ListNode;
import com.zxw.leetcode.type.tree.LeetCodeWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zxw
 * @date 2022/8/5 16:55
 */
@Slf4j
public class Palindrome {
    public static void main(String[] args) {
        Palindrome palindrome = new Palindrome();
        palindrome.isPalindrome(LeetCodeWrapper.stringToListNode("[1,2,2,1]"));
    }

    /**
     * 234.回文链表
     *
     * @param head
     * @return
     */
    ListNode slow = null;

    public boolean isPalindrome(ListNode head) {
        slow = head;
        return traverse(head);
    }

    public boolean traverse(ListNode head) {
        if (head == null) {
            return true;
        }
        boolean res = traverse(head.next) && (head.val == slow.val);
        slow = slow.next;
        return res;
    }
}
