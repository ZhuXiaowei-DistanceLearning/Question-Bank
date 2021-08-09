package com.zxw.thread;

import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zxw
 * @date 2020/8/26 10:18
 */
public class TimerSuperisorTask extends TimerTask {
    private final long timeoutMillis;
    private final ScheduledExecutorService scheduler;
    private final ThreadPoolExecutor executor;
    private final Runnable task;
    private final AtomicLong delay;
    private final long maxDelay;

    public TimerSuperisorTask(String name, ScheduledExecutorService scheduler, ThreadPoolExecutor executor, int timeout, TimeUnit timeUnit, int expBackOffBound, Runnable task) {
        this.scheduler = scheduler;
        this.executor = executor;
        this.timeoutMillis = timeUnit.toMillis(timeout);
        this.task = task;
        this.delay = new AtomicLong(timeoutMillis);
        this.maxDelay = timeoutMillis * expBackOffBound;
    }

    @Override
    public void run() {
        Future<?> future = null;
        try {
            future = executor.submit(task);
            future.get(timeoutMillis, TimeUnit.MILLISECONDS);  // block until done or timeout
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (!scheduler.isShutdown()) {
                scheduler.schedule(this, 30, TimeUnit.SECONDS);
            }
        }
    }
}
