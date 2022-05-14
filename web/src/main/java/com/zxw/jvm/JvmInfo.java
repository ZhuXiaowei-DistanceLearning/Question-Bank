package com.zxw.jvm;

/**
 * @author zxw
 * @date 2022/5/13 22:54
 */
public class JvmInfo {
    /**
     * -Xms：Java堆内存的大小
     * -Xmx：Java堆内存的最大大小
     * -Xmn：Java堆内存中的新生代大小，扣除新生代剩下的就是老年代的内存大小了
     * -XX:PermSize -XX:MetaspaceSize：永久代大小
     * -XX:MaxPermSize -XX:MaxMetaspaceSize：永久代最大大小
     * -Xss：每个线程的栈内存大小
     */
    public void param(){

    }

    /**
     * 我们的支付系统需要部署多少台机器？
     * 每台机器需要多大的内存空间？
     * 每台机器上启动的JVM需要分配多大的堆内存空间？
     * 给JVM多大的内存空间才能保证可以支撑这么多的支付订单在内存里的创建，而不会导致内存不够直接崩溃？
     */
    public void question(){

    }

    /**
     * 躲过15次GC之后进入老年代
     * 动态对象年龄判断:假如说当前放对象的Survivor区域里，一批对象的总大小大于了这块Survivor区域的内存大小的50%，那么此时大
     * 于等于这批对象年龄的对象，就可以直接进入老年代了。
     * 大对象直接进入老年代
     * -XX:PretenureSizeThreshold 比如“1048576”字节，就是1MB。
     * -XX:MaxTenuringThreshold
     * -XX:-HandlePromotionFailure
     * 标记整理算法
     * 就是尽可能让对象都在新生代里分配和回收，尽量别让太多对象频繁进入老年代，避免频繁对老年代进行垃圾回收，同时给系统充足的内存大小，避免新生代频繁的进行垃圾回收
     */
    public void old(){

    }
}
