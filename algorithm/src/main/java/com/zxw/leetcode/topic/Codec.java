package com.zxw.leetcode.topic;

import cn.hutool.core.lang.Assert;
import com.zxw.leetcode.swordoffer.data.TreeNode;

import java.util.LinkedList;
import java.util.Stack;

public class Codec {

    public static void main(String[] args) {
        Codec codec = new Codec();
        Assert.isTrue(codec.decodeString("3[a2[c]]").equals("accaccacc"));
    }

    /**
     * 394. 字符串解码
     * 本题难点在于括号内嵌套括号，需要从内向外生成与拼接字符串，这与栈的先入后出特性对应。
     * 算法流程：
     * <p>
     * 构建辅助栈 stack， 遍历字符串 s 中每个字符 c；
     * 当 c 为数字时，将数字字符转化为数字 multi，用于后续倍数计算；
     * 当 c 为字母时，在 res 尾部添加 c；
     * 当 c 为 [ 时，将当前 multi 和 res 入栈，并分别置空置 00：
     * 记录此 [ 前的临时结果 res 至栈，用于发现对应 ] 后的拼接操作；
     * 记录此 [ 前的倍数 multi 至栈，用于发现对应 ] 后，获取 multi × [...] 字符串。
     * 进入到新 [ 后，res 和 multi 重新记录。
     * 当 c 为 ] 时，stack 出栈，拼接字符串 res = last_res + cur_multi * res，其中:
     * last_res是上个 [ 到当前 [ 的字符串，例如 "3[a2[c]]" 中的 a；
     * cur_multi是当前 [ 到 ] 内字符串的重复倍数，例如 "3[a2[c]]" 中的 2。
     * 返回字符串 res。
     *
     * @param s
     * @return
     */
    public String decodeString(String s) {
        StringBuilder res = new StringBuilder();
        int multi = 0;
        Stack<Integer> stack_multi = new Stack<>();
        Stack<String> stack_res = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '[') {
                stack_multi.push(multi);
                stack_res.push(res.toString());
                multi = 0;
                res = new StringBuilder();
            } else if (c == ']') {
                StringBuilder tmp = new StringBuilder();
                int cur_multi = stack_multi.pop();
                for (int j = 0; j < cur_multi; j++) tmp.append(res);
                res = new StringBuilder(stack_res.pop() + tmp);
            } else if (c >= 48 && c <= 57) {
                multi = multi * 10 + Integer.parseInt(c + "");
            } else {
                res.append(c);
            }
        }
        return res.toString();
    }


    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        dfs(root, sb);
        return sb.toString();
    }

    public void dfs(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("#").append(",");
            return;
        }
        sb.append(root.val).append(",");
        dfs(root.left, sb);
        dfs(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        LinkedList<String> list = new LinkedList();
        for (String d : data.split(",")) {
            list.add(d);
        }
        return deserializeDfs(list);
    }

    public TreeNode deserializeDfs(LinkedList<String> list) {
        if (list.isEmpty()) {
            return null;
        }
        String data = list.pollFirst();
        if (data.equals("#")) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(data));
        root.left = deserializeDfs(list);
        root.right = deserializeDfs(list);
        return root;
    }
}