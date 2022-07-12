package com.zxw.leetcode.topic;

import cn.hutool.core.lang.Assert;
import com.zxw.leetcode.type.tree.LeetCodeWrapper;

/**
 * @author zxw
 * @date 2022/7/12 10:23
 */
public class DFS {
    public static void main(String[] args) {
        DFS dfs = new DFS();
        Assert.isTrue(dfs.exist(LeetCodeWrapper.stringToChar2dArray("[[\"A\",\"B\",\"C\",\"E\"],[\"S\",\"F\",\"C\",\"S\"],[\"A\",\"D\",\"E\",\"E\"]]"), "ABCCED"));
        Assert.isTrue(dfs.exist(LeetCodeWrapper.stringToChar2dArray("[[\"A\",\"B\",\"C\",\"E\"],[\"S\",\"F\",\"C\",\"S\"],[\"A\",\"D\",\"E\",\"E\"]]"), "SEE"));
        Assert.isTrue(!dfs.exist(LeetCodeWrapper.stringToChar2dArray("[[\"A\",\"B\",\"C\",\"E\"],[\"S\",\"F\",\"C\",\"S\"],[\"A\",\"D\",\"E\",\"E\"]]"), "ABCB"));
    }

    public boolean exist(char[][] board, String word) {
        int[][] mark = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, i, j, mark, word, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(char[][] board, int i, int j, int[][] mark, String word, int index) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || mark[i][j] == 1 || board[i][j] != word.charAt(index)) {
            return false;
        }
        if (index == word.length() - 1) {
            return true;
        } else {
            mark[i][j] = 1;
            // 上
            boolean b = dfs(board, i + 1, j, mark, word, index + 1) ||
                    // 下
                    dfs(board, i - 1, j, mark, word, index + 1) ||
                    // 左
                    dfs(board, i, j - 1, mark, word, index + 1) ||
                    // 右
                    dfs(board, i, j + 1, mark, word, index + 1);
            if (!b) {
                mark[i][j] = 0;
            }
            return b;
        }
    }
}
