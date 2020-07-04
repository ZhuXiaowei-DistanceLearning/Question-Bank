package com.zxw.jvm;

/**
 * @author zxw
 * @date 2020/6/28 15:26
 */
public class Test {
    public static void main(String[] args) {
//        testCollection();
//        testTenuringThreshold();
        int a = 1;
        int b = 2;
        int c = 100;
        int d = 200;
        int f = a + b;
        int g = c + d;
        System.out.println(a + b);
        System.out.println(c + d);
    }

    /**
     * -Xmx20m -Xms20m -XX:+PrintGCDetails -Xmn10m -XX:SurvivorRatio=8
     */
    public static void testCollection(){
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * 1024 * 1024];
        allocation2 = new byte[2 * 1024 * 1024];
        allocation3 = new byte[2 * 1024 * 1024];
        allocation4 = new byte[4 * 1024 * 1024];
    }

    /**
     * -Xmx20m -Xms20m -XX:+PrintGCDetails -Xmn10m -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1
     */
    public static void testTenuringThreshold(){
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[1024 * 1024 / 4];
        allocation2 = new byte[2 * 1024 * 1024];
        allocation3 = new byte[2 * 1024 * 1024];
        allocation3 = null;
        allocation3 = new byte[4 * 1024 * 1024];
    }

}
