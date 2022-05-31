package com.zxw.metrics;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 为什么要使用多线程？: 提升性能，从度量的角度，主要是降低延迟，提高吞吐量
 * 多线程的应用场景: 提升性能本质上就是提升硬件的利用率，再具体点来说，就是提升I/O的利用率和CPU的利用率
 * CPU密集型：CPU核数
 * IO密集型：最佳线程数=CPU核数 * [ 1 +（I/O耗时 / CPU耗时）]
 * @author zxw
 * @date 2022/5/30 15:21
 */
public class ThreadMetrics {

    static Object lock = new Object();

    /**
     * 1. 吞吐量：指的是单位时间内能处理的请求数量。吞吐量越高，说明性能越好。
     * 2. 延迟：指的是从发出请求到收到响应的时间。延迟越小，说明性能越好。
     * 3. 并发量：指的是能同时处理的请求数量，一般来说随着并发量的增加、延迟也会增加。所以延迟这个指标，一般都会是基于并发量来说的。例如并发量是1000的时候，延迟是50毫秒。
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        lifeCycle();
    }

    /**
     * 并发编程里两大核心问题——互斥和同步
     * 1. Hasen模型里面，要求notify()放在代码的最后，这样T2通知完T1后，T2就结束了，然后
     * T1再执行，这样就能保证同一时刻只有一个线程执行。
     * 2. Hoare模型里面，T2通知完T1后，T2阻塞，T1马上执行；等T1执行完，再唤醒T2，也能
     * 保证同一时刻只有一个线程执行。但是相比Hasen模型，T2多了一次阻塞唤醒操作。
     * 3. MESA管程里面，T2通知完T1后，T2还是会接着执行，T1并不立即执行，仅仅是从条件变
     * 量的等待队列进到入口等待队列里面。这样做的好处是notify()不用放到代码的最后，T2也
     * 没有多余的阻塞唤醒操作。但是也有个副作用，就是当T1再次执行的时候，可能曾经满足
     * 的条件，现在已经不满足了，所以需要以循环方式检验条件变量。
     */
    public void test1() {

    }

    /**
     * 通用的线程生命周期基本上可以用五个状态表示分别是：初始状态、可运行状态、运行状态、休眠状态和终止状态。
     */
    public static void lifeCycle() throws InterruptedException {
        AtomicInteger i = new AtomicInteger();
        Thread t1 = new Thread(() -> {
            while (true) {
                System.out.println("当前结果:" + i.get() % 5);
                // TIME-WAITING
                if (i.get() % 5 == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
//                    1. 调用带超时参数的Thread.sleep(long millis)方法；
//                    2. 获得synchronized隐式锁的线程，调用带超时参数的Object.wait(long timeout)方法；
//                    3. 调用带超时参数的Thread.join(long millis)方法；
//                    4. 调用带超时参数的LockSupport.parkNanos(Object blocker, long deadline)方法；
//                    5. 调用带超时参数的LockSupport.parkUntil(long deadline)方法。
                }
                // WAITING
                if (i.get() % 5 == 1) {
                    LockSupport.park(lock);
                    System.out.println("park执行完毕");
                }
                if (i.get() % 5 == 2) {
                    synchronized (lock) {
                        try {
                            System.out.println("执行wait方法");
                            Thread.sleep(2000);
                            lock.wait();
                            // Thread.join()
                            // LockSupport.park()
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (i.get() % 5 == 3) {
                    synchronized (lock) {
                        System.out.println("获取到锁");
                    }
                }
                if (i.get() % 5 == 4) {

                }
                i.incrementAndGet();
            }
        });
        t1.start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("当前线程状态:" + t1.getState());
                if (i.get() % 5 == 1) {
                    LockSupport.unpark(t1);
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    try {
                        System.out.println("获取到锁");
                        Thread.sleep(2000);
                        lock.notifyAll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
        Thread.currentThread().join();
    }
}
