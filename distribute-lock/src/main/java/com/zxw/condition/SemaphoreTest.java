package com.zxw.condition;

import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zxw
 * @date 2022/5/31 15:47
 */
public class SemaphoreTest {
    static int count;
    //初始化信号量
    // nit()：设置计数器的初始值。
    // down()：计数器的值减1；如果此时计数器的值小于0，则当前线程将被阻塞，否则当前线
    //程可以继续执行。
    // up()：计数器的值加1；如果此时计数器的值小于或者等于0，则唤醒等待队列中的一个线
    //程，并将其从等待队列中移除。
    static final Semaphore s = new Semaphore(1);

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                addOne();
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                addOne();
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t2").start();

    }

    //用信号量保证互斥
    @SneakyThrows
    static void addOne() {
        // down
        s.acquire();
        try {
            count += 1;
            System.out.println(Thread.currentThread().getName() + "执行操作");
        } finally {
            // up
            s.release();
        }
    }

}
