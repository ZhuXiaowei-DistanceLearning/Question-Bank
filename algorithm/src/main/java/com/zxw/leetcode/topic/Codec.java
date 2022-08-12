package com.zxw.leetcode.topic;

import com.zxw.leetcode.swordoffer.data.TreeNode;

import java.util.LinkedList;

public class Codec {


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
        if(list.isEmpty()){
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