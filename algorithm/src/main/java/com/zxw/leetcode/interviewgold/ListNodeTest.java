package com.zxw.leetcode.interviewgold;

import com.zxw.datastruct.ListNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author zxw
 * @date 2020/7/16 10:20
 */
public class ListNodeTest {
    public static void main(String[] args) {
        ListNodeTest listNodeTest = new ListNodeTest();
        ListNode node = new ListNode(4);
        node.next = new ListNode(5);
        node.next.next = new ListNode(1);
        node.next.next.next = new ListNode(9);
        listNodeTest.deleteNode(node);
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
            head = head.next;
        }
        return node.val;
    }

    /**
     * 面试题 02.03. 删除中间节点
     * 实现一种算法，删除单向链表中间的某个节点（即不是第一个或最后一个节点），假定你只能访问该节点。
     * 示例：
     * 输入：单向链表a->b->c->d->e->f中的节点c
     * 结果：不返回任何数据，但该链表变为a->b->d->e->f
     *
     * @param node
     */
    public void deleteNode(ListNode node) {
        ListNode fast = node;
        ListNode slow = node;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
    }

    /**
     * 面试题 02.04. 分割链表
     * 编写程序以 x 为基准分割链表，使得所有小于 x 的节点排在大于或等于 x 的节点之前。如果链表中包含 x，x 只需出现在小于 x 的元素之后(如下所示)。分割元素 x 只需处于“右半部分”即可，其不需要被置于左右两部分之间。
     * 示例:
     * 输入: head = 3->5->8->5->10->2->1, x = 5
     * 输出: 3->1->2->10->5->5->8
     *
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        return null;
    }

    /**
     * 面试题 02.05. 链表求和
     * 给定两个用链表表示的整数，每个节点包含一个数位。
     * 这些数位是反向存放的，也就是个位排在链表首部。
     * 编写函数对这两个整数求和，并用链表形式返回结果。
     * 示例：
     * 输入：(7 -> 1 -> 6) + (5 -> 9 -> 2)，即617 + 295
     * 输出：2 -> 1 -> 9，即912
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        boolean flag = false;
        ListNode listNode = l1;
        while (l1 != null && l2 != null) {
            int i = l1.val + l2.val;
            if (flag) {
                i++;
            }
            if (i < 10) {
                l1.val = i;
                flag = false;
            } else {
                l1.val = i % 10;
                flag = true;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        if (l2 != null) {
            if (l1 == null) {
                listNode.next = l2;
            } else {
                l1.next = l2;
            }
        }
        if (flag) {
            listNode.next = new ListNode(1);
        }
        return listNode;
    }

    /**
     * 面试题 02.06. 回文链表
     * 编写一个函数，检查输入的链表是否是回文的。
     * 示例 1：
     * 输入： 1->2
     * 输出： false
     *
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        List list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }

        for (int i = 0; i < list.size() / 2; i++) {
            if (!list.get(i).equals(list.get(list.size() - i - 1))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 面试题 02.07. 链表相交
     * 给定两个（单向）链表，判定它们是否相交并返回交点。请注意相交的定义基于节点的引用，而不是基于节点的值。换句话说，如果一个链表的第k个节点与另一个链表的第j个节点是同一节点（引用完全相同），则这两个链表相交。
     * 示例 1：
     * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
     * 输出：Reference of the node with value = 8
     * 输入解释：相交节点的值为 8 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,0,1,8,4,5]。在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode l1 = headA;
        ListNode l2 = headB;
        while (l1 != l2) {
            l1 = l1 == null ? headB : l1.next;
            l2 = l2 == null ? headA : l2.next;
        }
        return l1;
    }

    /**
     * 链表、数组、子串
     * 双指针法：
     * 快慢指针：判断链表中是否有环
     * 滑动窗口：
     * 面试题 02.08. 环路检测
     * 给定一个链表，如果它是有环链表，实现一个算法返回环路的开头节点。
     * 有环链表的定义：在链表中某个节点的next元素指向在它前面出现过的节点，则表明该链表存在环路。
     *  
     * 示例 1：
     * 输入：head = [3,2,0,-4], pos = 1
     * 输出：tail connects to node index 1
     * 解释：链表中有一个环，其尾部连接到第二个节点。
     *
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }
        if (fast == null || fast.next == null) {
            return null;
        }
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
