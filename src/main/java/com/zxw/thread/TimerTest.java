package com.zxw.thread;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zxw
 * @date 2020/8/26 10:32
 */
public class TimerTest {
    public static void main(String[] args) {
        Timer timer = new Timer();
        Timer timer2 = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行");
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("执行2");
            }
        };
        // 固定时间的调度方式，延迟一秒，之后每隔一秒打印一次
        // 打印结果如下：
        // 11:03:55, called
        // 11:03:56, called
        // 11:03:57, called
        // 11:03:58, called
        // 11:03:59, called
        // ...
        timer.schedule(task, 1000, 1000);
        timer2.schedule(task2, 1000, 1000);
    }
}
