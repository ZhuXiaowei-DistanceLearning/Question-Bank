package com.zxw.leetcode.topic;

import com.zxw.datastruct.ListNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 双指针专题训练
 *
 * @author zxw
 * @date 2020/11/5 10:38
 */
public class DoublePointer {
    public static void main(String[] args) {

    }


    /**
     * 160.相交链表
     * 链表交替执行
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;
        while (p1 != p2) {
            if (p1 != null) {
                p1 = p1.next;
            } else {
                p1 = headB;
            }
            if (p2 != null) {
                p2 = p2.next;
            } else {
                p2 = headA;
            }
        }
        return p1;
    }

    /**
     * 148. 排序链表
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        ListNode res = new ListNode(-1);
        ListNode node = res;
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        Collections.sort(list);
        for (Integer i : list) {
            node.next = new ListNode(i);
            node = node.next;
        }
        return res.next;
    }

    /**
     * 19. 删除链表的倒数第N个节点
     * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
     * 示例：
     * 给定一个链表: 1->2->3->4->5, 和 n = 2.
     * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
     * 1.通过倒数的节点数，让快指针前进N步
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        ListNode slow = head;
        while (n-- > 0) {
            fast = fast.next;
        }
        if (fast == null) {
            return head.next;
        }
        while (fast != null && fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return head;
    }

    /**
     * 167. 两数之和 II - 输入有序数组
     * 给定一个已按照升序排列 的有序数组，找到两个数使得它们相加之和等于目标数。
     * 函数应该返回这两个下标值 index1 和 index2，其中 index1 必须小于 index2。
     * 说明:
     * 返回的下标值（index1 和 index2）不是从零开始的。
     * 你可以假设每个输入只对应唯一的答案，而且你不可以重复使用相同的元素。
     * 示例:
     * 输入: numbers = [2, 7, 11, 15], target = 9
     * 输出: [1,2]
     * 解释: 2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。
     *
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                // 题目要求的索引是从 1 开始的
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                left++; // 让 sum 大一点
            } else if (sum > target) {
                right--; // 让 sum 小一点
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * 344. 反转字符串
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     * 你可以假设数组中的所有字符都是 ASCII 码表中的可打印字符。
     *  
     * 示例 1：
     * 输入：["h","e","l","l","o"]
     * 输出：["o","l","l","e","h"]
     * 示例 2：
     * 输入：["H","a","n","n","a","h"]
     * 输出：["h","a","n","n","a","H"]
     *
     * @param s
     */
    public void reverseString(char[] s) {
        int l = 0;
        int r = s.length - 1;
        while (l < r) {
            char mid = s[l];
            s[l] = s[r];
            s[r] = mid;
            l++;
            r--;
        }
    }

    /**
     * 面试题 02.02. 返回倒数第 k 个节点
     * 实现一种算法，找出单向链表中倒数第 k 个节点。返回该节点的值。
     * <p>
     * 注意：本题相对原题稍作改动
     * <p>
     * 示例：
     * <p>
     * 输入： 1->2->3->4->5 和 k = 2
     * 输出： 4
     *
     * @param head
     * @return
     */
    public int kthToLast(ListNode head, int k) {
        ListNode fast = head;
        while (k-- > 0) {
            fast = fast.next;
        }
        if (fast == null) {
            return head.val;
        }
        while (fast != null && fast.next != null) {
            fast = fast.next;
            head = head.next;
        }
        return head.next.val;
    }

    /**
     * 面试题 02.03. 删除中间节点
     * 实现一种算法，删除单向链表中间的某个节点（即不是第一个或最后一个节点），假定你只能访问该节点。
     *  
     * 示例：
     * 输入：单向链表a->b->c->d->e->f中的节点c
     * 结果：不返回任何数据，但该链表变为a->b->d->e->f
     *
     * @param node
     */
    public void deleteNode(ListNode node) {
        ListNode fast = node;
        ListNode slow = node;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        slow = slow.next;
    }

}
