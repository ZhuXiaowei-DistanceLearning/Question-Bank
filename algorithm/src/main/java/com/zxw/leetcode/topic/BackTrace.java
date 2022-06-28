package com.zxw.leetcode.topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zxw
 * @date 2022/6/28 11:33
 */
public class BackTrace {


    public static void main(String[] args) {
        BackTrace backTrace = new BackTrace();
        backTrace.generateParenthesis(3);
    }

    public List<String> generateParenthesis(int n) {
        if (n == 0) {
            return Collections.EMPTY_LIST;
        }
        List<String> res = new ArrayList<>();
        generateParenthesisDFS(n, n, "", res);
        return res;
    }

    /**
     * 1、一个「合法」括号组合的左括号数量一定等于右括号数量，这个很好理解。
     * <p>
     * 2、对于一个「合法」的括号字符串组合 p，必然对于任何 0 <= i < len(p) 都有：子串 p[0..i] 中左括号的数量都大于或等于右括号的数量。
     *
     * @param l：左括号剩余数量
     * @param r：右括号剩余数量
     * @return
     */
    public void generateParenthesisDFS(int l, int r, String s, List<String> res) {
        // 对于任意子串，如果右边括号大于左边括号数，那么一定不合法
        if (l > r) {
            return;
        }
        // 如果可用括号数为0了，那么也一定不合法
        if (l < 0 || r < 0) {
            return;
        }
        if (l == 0 && r == 0) {
            res.add(s);
            return;
        }
        String t = s;
        generateParenthesisDFS(l - 1, r, t += "(", res);
        t = s;
        generateParenthesisDFS(l, r - 1, t += ")", res);
    }
}
