package com.zxw.jmh;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.*;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class Test {

    // 创建一个 SubmissionPublisher 对象
    private static SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

    // 创建一个 CompletableFuture 对象
    private static CompletableFuture<Long> future = new CompletableFuture<>();

    // 创建一个 Flow.Subscriber 对象
    private static Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<>() {
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

    // 创建一个 LinkedBlockingQueue 对象
    private static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1000000);

    // 创建一个 ExecutorService 对象
    private static ExecutorService executor = Executors.newFixedThreadPool(2);

    // 创建一个 Callable 任务，用来向阻塞队列中插入数据
    private static Callable<Void> producerTask = () -> {
        for (int i = 1; i <= 1000000; i++) {
            queue.put(i); // 向队列中插入数据
        }
        return null;
    };

    // 创建一个 Callable 任务，用来从阻塞队列中取出数据，并计算它们的和
    private static Callable<Long> consumerTask = () -> {
        long sum = 0L; // 用来存储数据之和
        for (int i = 1; i <= 1000000; i++) {
            sum += queue.take(); // 从队列中取出数据并累加
        }
        return sum;
    };

    @Benchmark
    public void testFlow() throws Exception {
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
    }

    @Benchmark
    public void testLinkedBlockingQueue() throws Exception {
        // 提交两个任务到线程池中执行
        Future<Void> producerFuture = executor.submit(producerTask);
        Future<Long> consumerFuture = executor.submit(consumerTask);

        // 等待两个任务执行完毕，并获取结果
        producerFuture.get();
        long queueSum = consumerFuture.get();
    }
}
