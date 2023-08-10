package com.zxw.reactive;

import java.util.concurrent.*;

public class Test {

    public static void main(String[] args) throws Exception {
        // 创建一个 SubmissionPublisher 对象
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        // 创建一个 CompletableFuture 对象
        CompletableFuture<Long> future = new CompletableFuture<>();

        // 创建一个 Flow.Subscriber 对象
        Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<>() {
            private Flow.Subscription subscription;
            private long sum = 0L; // 用来存储数据之和

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1); // 请求第一个数据
            }

            @Override
            public void onNext(Integer item) {
                sum += item; // 累加数据
                subscription.request(1); // 请求下一个数据
            }

            @Override
            public void onError(Throwable throwable) {
                future.completeExceptionally(throwable); // 发生异常时，完成 CompletableFuture
            }

            @Override
            public void onComplete() {
                future.complete(sum); // 完成时，返回数据之和
            }
        };

        // 记录 Flow 的开始时间
        long flowStartTime = System.currentTimeMillis();

        // 订阅 Flow.Publisher
        publisher.subscribe(subscriber);

        // 发射 1000000 个整数
        for (int i = 1; i <= 1000000; i++) {
            publisher.submit(i);
        }

        // 关闭 Flow.Publisher
        publisher.close();

        // 获取 Flow.Subscriber 的结果
        long flowSum = future.get();

        // 记录 Flow 的结束时间
        long flowEndTime = System.currentTimeMillis();

        // 计算并输出 Flow 的执行时间和结果
        long flowTime = flowEndTime - flowStartTime;
        System.out.println("Flow 的执行时间为 " + flowTime + " 毫秒");
        System.out.println("Flow 的和为 " + flowSum);

        // 创建一个 LinkedBlockingQueue 对象
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1000000);

        // 创建一个 ExecutorService 对象
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 创建一个 Callable 任务，用来向阻塞队列中插入数据
        Callable<Void> producerTask = () -> {
            for (int i = 1; i <= 1000000; i++) {
                queue.put(i); // 向队列中插入数据
            }
            return null;
        };

        // 创建一个 Callable 任务，用来从阻塞队列中取出数据，并计算它们的和
        Callable<Long> consumerTask = () -> {
            long sum = 0L; // 用来存储数据之和
            for (int i = 1; i <= 1000000; i++) {
                sum += queue.take(); // 从队列中取出数据并累加
            }
            return sum;
        };

        // 记录 LinkedBlockingQueue 的开始时间
        long queueStartTime = System.currentTimeMillis();

        // 提交两个任务到线程池中执行
        Future<Void> producerFuture = executor.submit(producerTask);
        Future<Long> consumerFuture = executor.submit(consumerTask);

        // 等待两个任务执行完毕，并获取结果
        producerFuture.get();
        long queueSum = consumerFuture.get();

        // 记录 LinkedBlockingQueue 的结束时间
        long queueEndTime = System.currentTimeMillis();

        // 计算并输出 LinkedBlockingQueue 的执行时间和结果
        long queueTime = queueEndTime - queueStartTime;
        System.out.println("LinkedBlockingQueue 的执行时间为 " + queueTime + " 毫秒");
        System.out.println("LinkedBlockingQueue 的和为 " + queueSum);

        // 关闭线程池
        executor.shutdown();
    }
}
