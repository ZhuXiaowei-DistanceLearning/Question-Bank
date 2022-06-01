package com.zxw.condition;

import java.util.concurrent.*;

/**
 * @author zxw
 * @date 2022/6/1 15:53
 */
public class CompletableFutureTest {

    public void test1() {
        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // 创建CompletionService
        CompletionService<Integer> cs = new ExecutorCompletionService<>(executor);
        // 异步向电商S1询价
        cs.submit(() -> 0);
        // 异步向电商S2询价
        cs.submit(() -> 0);
        // 异步向电商S3询价
        cs.submit(() -> 0);
        // 将询价结果异步保存到数据库
        for (int i = 0; i < 3; i++) {
            Integer r = null;
            try {
                r = cs.take().get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            executor.execute(() -> {
            });
        }
    }

    public static void main(String[] args) {
        test();
    }

    /**
     * CompletionStage接口里面描述串行关系，主要是thenApply、thenAccept、thenRun和thenCompose这四个系列的接口。
     * thenApply系列函数里参数fn的类型是接口Function<T, R>，这个接口里与
     * CompletionStage相关的方法是 R apply(T t)，这个方法既能接收参数也支持返回值，所
     * 以thenApply系列方法返回的是CompletionStage<R>。
     * 而thenAccept系列方法里参数consumer的类型是接口Consumer<T>，这个接口里与
     * CompletionStage相关的方法是 void accept(T t)，这个方法虽然支持参数，但却不支
     * 持回值，所以thenAccept系列方法返回的是CompletionStage<Void>。
     * thenRun系列方法里action的参数是Runnable，所以action既不能接收参数也不支持返回
     * 值，所以thenRun系列方法返回的也是CompletionStage<Void>。
     * 这些方法里面Async代表的是异步执行fn、consumer或者action。其中，需要你注意的是
     * thenCompose系列方法，这个系列的方法会新创建出一个子流程，最终结果和thenApply系
     * 列是相同的。
     */
    public static void test() {
        CompletableFuture<String> f0 = CompletableFuture.supplyAsync(() -> "Hello World") //①
                .thenApply(s -> s + " QQ") //②
                .thenApply(String::toUpperCase);//③
        System.out.println(f0.join());
    }

    /**
     * CompletionStage接口里面描述AND汇聚关系，主要是thenCombine、thenAcceptBoth和runAfterBoth系列的接口，
     * 这些接口的区别也是源自fn、consumer、action这三个核心参数不同。它们的使用你可以参考上面烧水泡茶的实现程序，这里就不赘述了。
     */
    public static void and() {

    }

    /**
     * CompletionStage接口里面描述OR汇聚关系，主要是applyToEither、acceptEither和runAfterEither系列的接口，
     * 这些接口的区别也是源自fn、consumer、action这三个核心参数不同。
     */
    public static void or() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            int t = getRandom(5, 10);
            sleep(t, TimeUnit.SECONDS);
            return String.valueOf(t);
        });
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            int t = getRandom(5, 10);
            sleep(t, TimeUnit.SECONDS);
            return String.valueOf(t);
        });
        CompletableFuture<String> f3 = f1.applyToEither(f2, s -> s);
        System.out.println(f3.join());
    }

    /**
     * CompletionStage exceptionally(fn);
     * CompletionStage<R> whenComplete(consumer);
     * CompletionStage<R> whenCompleteAsync(consumer);
     * CompletionStage<R> handle(fn);
     * CompletionStage<R> handleAsync(fn);
     */
    public static void exception() {
        CompletableFuture<Integer> f0 = CompletableFuture.supplyAsync(() -> (7 / 0)).thenApply(r -> r * 10).exceptionally(e -> 0);
        System.out.println(f0.join());
    }

    private static int getRandom(int i, int i1) {
        return 0;
    }

    static CompletableFuture createFuture(String id) {
        //任务1：洗水壶->烧开水
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            System.out.println("T1:洗水壶...");
            sleep(1, TimeUnit.SECONDS);
            System.out.println("T1:烧开水...");
            sleep(15, TimeUnit.SECONDS);
        });
//任务2：洗茶壶->洗茶杯->拿茶叶
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("T2:洗茶壶...");
            sleep(1, TimeUnit.SECONDS);
            System.out.println("T2:洗茶杯...");
            sleep(2, TimeUnit.SECONDS);
            System.out.println("T2:拿茶叶...");
            sleep(1, TimeUnit.SECONDS);
            return "龙井";
        });
//任务3：任务1和任务2完成后执行：泡茶
        CompletableFuture<String> f3 = f1.thenCombine(f2, (__, tf) -> {
            System.out.println("T1:拿到茶叶:" + tf);
            System.out.println("T1:泡茶...");
            return "上茶:" + tf;
        });
//等待任务3执行结果
        System.out.println(f3.join());
        return f3;
    }

    public static void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
        }
    }
}
