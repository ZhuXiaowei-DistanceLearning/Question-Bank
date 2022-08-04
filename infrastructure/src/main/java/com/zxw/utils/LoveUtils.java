package com.zxw.utils;

/**
 * @author zxw
 * @date 2022/8/4 12:25
 */
public class LoveUtils {

    public static void main(String[] args) {
//        test2();
        test1();
    }

    public static void test1() {
        int cx = 0, cy = 0; // 问y 、 x 总共循环了多少次
        float dx = 0.05f, dy = 0.1f;
        int stars = 0;//计数器
        for (float y = (float) 1.5; y > -1.5; y -= dy, cy++) {
            cx = 0;
            for (float x = (float) -1.5; x < 1.5; x += dx, cx++) {
                float a = x * x + y * y - 1;
                if ((a * a * a - x * x * y * y * y) <= 0.0) {
                    System.out.print("*");// 心形区域内打印*
                    stars++;
                } else {
                    System.out.print(" "); // 心形区域外打印空格
                }
            }
            System.out.println("");    // 本行结束，换行准备下一行的输出
        }

        System.out.printf("cx=%d, cy=%d%n", cx, cy);
        System.out.printf("count=%d", stars);
    }

    private static void test2() {
        int n = 4;
        int i, j, m;
        for (i = 1; i <= n; i++) {
            for (j = 1; j <= 2 * (n - i) - 1; j++) {
                System.out.print(" ");
            }
            for (j = 1; j <= 6 + (10 - i) * (i - 1) / 2; j++) {
                System.out.print("*");
            }
            for (j = 1; j <= 2 * n - 1 + (i - 6) * (i - 1); j++) {
                if (i == n)
                    break;
                System.out.print(" ");
            }
            for (j = 1; j <= 6 + (10 - i) * (i - 1) / 2; j++) {
                if (i == n)
                    break;
                System.out.print("*");
            }
            if (i == n) {
                for (j = 1; j <= 6 + (10 - i) * (i - 1) / 2 - 1; j++) {
                    System.out.print("*");
                }
            }
            System.out.println("");
        } // 上面部分
        for (i = 1; i <= n - 2; i++) {
            for (j = 1; j <= 6 + (10 - n) * (n - 1) / 2 - 1 + 6 + (10 - n) * (n - 1) / 2; j++) {
                System.out.print("*");
            }
            System.out.println("");
        } // 中间部分
        m = 6 + (10 - n) * (n - 1) / 2 + 6 + (10 - n) * (n - 1) / 2 - 1;
        for (i = 1; i <= (m - 2 - 3) / 4 + 1; i++) {
            for (j = 1; j <= 2 * i - 1; j++) {
                System.out.print(" ");
            }
            for (j = 1; j <= m + 2 - 4 * i; j++) {
                System.out.print("*");
            }
            System.out.println("");
        }
        for (j = 1; j <= 2 * ((m - 2 - 3) / 4 + 1); j++) {
            System.out.print(" ");
        }
        System.out.println("*");// 下面部分
    }
}
