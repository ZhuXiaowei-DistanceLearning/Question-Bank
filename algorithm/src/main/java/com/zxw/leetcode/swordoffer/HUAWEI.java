package com.zxw.leetcode.swordoffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author zxw
 * @date 2023/5/20 17:24
 */
public class HUAWEI {

    static List<Integer> set = new ArrayList();
    static int size = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int a = in.nextInt();
            if (size == 0) {
                size = a;
                continue;
            }
            size--;
            if (!set.contains(a)) {
                set.add(a);
            } else if (size != 0) {
                {
                    continue;
                }
            }
            if (size == 0) {
                Collections.sort(set);
                for (Integer e : set) {
                    System.out.println(e);
                }
            }

        }
    }

}
