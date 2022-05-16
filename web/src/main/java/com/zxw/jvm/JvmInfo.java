package com.zxw.jvm;

/**
 * @author zxw
 * @date 2022/5/13 22:54
 */
public class JvmInfo {
    /**
     * -Xms2048M：Java堆内存的大小
     * -Xmx2048M：Java堆内存的最大大小
     * -Xmn2048M：Java堆内存中的新生代大小，扣除新生代剩下的就是老年代的内存大小了
     * -XX:PermSize=256M -XX:MetaspaceSize=256M：永久代大小
     * -XX:MaxPermSize=256M -XX:MaxMetaspaceSize=256M：永久代最大大小
     * -Xss1M：每个线程的栈内存大小
     * -XX:SurvivorRatio=8
     * -XX:NewRatio=2
     * -XX:InitialHeapSize：初始堆大小
     * -XX:MaxHeapSize：最大堆大小
     * -XX:NewSize：初始新生代大小
     * -XX:MaxNewSize：最大新生代大小
     * -XX:+PrintGCDetils：打印详细的gc日志
     * -XX:+PrintGCTimeStamps：这个参数可以打印出来每次GC发生的时间
     * -Xloggc:gc.log：这个参数可以设置将gc日志写入一个磁盘文件
     *
     */
    public void param(){

    }

    /**
     * 我们的支付系统需要部署多少台机器？
     * 每台机器需要多大的内存空间？
     * 每台机器上启动的JVM需要分配多大的堆内存空间？
     * 给JVM多大的内存空间才能保证可以支撑这么多的支付订单在内存里的创建，而不会导致内存不够直接崩溃？
     * Eden区的对象增长速率多快？
     * Young GC频率多高
     * 一次 Young GC多长耗时
     * Young GC过后多少对象存货
     * 老年代的对象增长速率多高
     * FULL GC频率多高
     * 一次FULL GC耗时
     */
    public void question(){

    }

    /**
     * 1.躲过15次GC之后进入老年代
     * 2.大对象直接进入老年代
     * 3.一次Young GC过后存货对象太多，Survivor区放不了
     * 4.动态对象年龄判断:假如说当前放对象的Survivor区域里，一批对象的总大小大于了这块Survivor区域的内存大小的50%，那么此时大于等于这批对象年龄的对象，就可以直接进入老年代了。
     * -XX:PretenureSizeThreshold=1M 比如“1048576”字节，就是1MB。
     * -XX:MaxTenuringThreshold=5
     * -XX:-HandlePromotionFailure
     * 标记整理算法
     * 就是尽可能让对象都在新生代里分配和回收，尽量别让太多对象频繁进入老年代，避免频繁对老年代进行垃圾回收，同时给系统充足的内存大小，避免新生代频繁的进行垃圾回收
     */
    public void old(){

    }

    /**
     * 标记清除算法
     * 优点：
     * 缺点：占用额外线程并发清除，浮动垃圾，Stop the World
     * -XX:CMSInitiatingOccupancyFaction：设置老年代占用多少比例的时候触发CMS垃圾回收
     * -XX:+UseCMSCompactAtFullCollection：在Full GC之后要再次进行“Stop the World”，停止工作线程，然后进行碎片
     * 整理，就是把存活对象挪到一起，空出来大片连续内存空间，避免内存碎片
     * -XX:CMSFullGCsBeforeCompaction：是每次Full GC之后都会进行一次内存整理。
     */
    public void CMS(){

    }

    /**
     * G1就会用之前说过的复制算法来进行垃圾回收，进入一个“Stop the World”状态,一旦失败，立马就会切换为停止系统程序，然后采用单线程进行标记、清理和压缩整理，空闲出来一批Region，这个过程是极慢极慢的
     * 优点：在一个时间内，垃圾回收导致的系统停顿时间不能超过多久
     * -XX:+UseG1GC
     * -XX:G1HeapRegionSize：指定region大小
     * -XX:G1NewSizePercent：设置新生代初始占比的 默认5%
     * -XX:G1MaxNewSizePercent：新生代最大内存占用比例
     * -XX:MaxGCPauseMills：指定系统停顿时长
     * -XX:InitiatingHeapOccupancyPercent：默认45% 新生代+老年代一起回收的混合回收阶段
     * -XX:G1MixedGCCountTarget：就是在一次混合回收的过程中，最后一个阶段执行几次混合回收，默认值是8次
     * 意味着最后一个阶段，先停止系统运行，混合回收一些Region，再恢复系统运行，接着再次禁止系统运行，混合回收一些Region，反复8次
     * XX:G1HeapWastePercent：在混合回收的时候，对Region回收都是基于复制算法进行的，都是把要回收的Region里的存活对象放入其他
     * Region，然后这个Region中的垃圾对象全部清理掉 默认值是5%
     * -XX:G1MixedGCLiveThresholdPercen：默认值是85%，意思就是确定要回收的Region的时候，必须是存活对象低于85%的Region才可以进行回收
     */
    public void G1(){

    }

    /**
     * 1.发生Young GC之前进行检查，如果“老年代可用的连续内存空间” < “新生代历次Young GC后升入老年代的对象总和的平均大小”，说明本次Young GC后可能升入老年代的对象大小，可能超过了老年代当前可用内存空间
     * 2.执行Young GC之后有一批对象需要放入老年代，此时老年代就是没有足够的内存空间存放这些对象了，此时必须立即触发一次Old GC
     * 3.老年代内存使用率超过了92%，也要直接触发Old GC，当然这个比例是可以通过参数调整的
     *
     * 1.系统高并发请求或者数据量过大，导致Young GC过后存活对象太多，内存分配不合理
     * 2.系统一次性加载过多数据进内存
     * 3.永久代因为加载类过多触发FULL GC
     *
     */
    public void fullGC(){

    }

    /**
     * jmap -histo PID：对象内存占用情况
     * jmap -heap PID：堆内存情况
     * jmap -dump:live,format=b,file=dump.hprof PID
     * jhat dump.hprof -port 7000
     */
    public void jmap(){

    }
}
