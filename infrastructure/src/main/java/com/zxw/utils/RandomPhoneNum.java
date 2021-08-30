package com.zxw.utils;

import java.util.Scanner;

public class RandomPhoneNum {
    static Scanner sc = new Scanner(System.in);

    public static <lenPhone> void main(String[] args) {
        //询问需要多少个随机号码
        boolean boo = true;
        int num = 0;
        do {
            //如果用户输入的不是一个整数，就循环要求用户输入一个整数
            System.out.println("你需要多少组电话号码，请输入一个整数");
//      String answer = sc.next();
            try {
                //将用户的输入转化为整数
//        num = Integer.parseInt(answer);
                num = 1;
                //如果转换成功，boo就设置为false使其可以跳出循环
                boo = false;
            } catch (Exception e) {
                //如果用户输入的不是一个整数，就抛出异常，要求用户重新输入
                System.out.println("你输入的不是一个整数，请重新输入");
            }

        } while (boo);

        System.out.println("你要的手机号码如下：");
        //将循环次数设置为用户需要的号码的数量
        for (int i = 0; i < num; i++) {
            //调用静态方法生成手机号码
            getPhoneNum();
        }
    }

    //定一个静态方法，专门生成单个的号码
    public static String getPhoneNum() {
        //给予真实的初始号段，号段是在百度上面查找的真实号段
        String[] start = {"133", "149", "153", "173", "177",
                "180", "181", "189", "199", "130", "131", "132",
                "145", "155", "156", "166", "171", "175", "176", "185", "186", "166", "134", "135",
                "136", "137", "138", "139", "147", "150", "151", "152", "157", "158", "159", "172",
                "178", "182", "183", "184", "187", "188", "198", "170", "171"};

        //随机出真实号段  使用数组的length属性，获得数组长度，
        //通过Math.random（）*数组长度获得数组下标，从而随机出前三位的号段
        String phoneFirstNum = start[(int) (Math.random() * start.length)];
        //随机出剩下的8位数
        String phoneLastNum = "";
        //定义尾号，尾号是8位
        final int LENPHONE = 8;
        //循环剩下的位数
        for (int i = 0; i < LENPHONE; i++) {
            //每次循环都从0~9挑选一个随机数
            phoneLastNum += (int) (Math.random() * 10);
        }
        //最终将号段和尾数连接起来
        String phoneNum = phoneFirstNum + phoneLastNum;
        return phoneNum;
    }
}