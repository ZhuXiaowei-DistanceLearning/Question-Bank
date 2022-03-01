package com.zxw.threadB;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zxw
 * @date 2022/3/1 15:13
 */
@Slf4j
public class ConMapTest {
    //线程个数
    private static int THREAD_COUNT = 10;
    //总元素数量
    private static int ITEM_COUNT = 1000;
    //循环次数
    private static int LOOP_COUNT = 10000000;

    public static void main(String[] args) throws InterruptedException {
//        normaluse();
//        gooduse();
        good();
    }

    private ConcurrentHashMap<String, Long> getData(int count) {
//        return LongStream.rangeClosed(1, count)
//                .boxed()
//                .collect(Collectors.toConcurrentMap(i -> UUID.randomUUID().toString(), (o1, o2) ->
//                        o1, ConcurrentHashMap::new));
        return null;
    }

    public String wrong() throws InterruptedException {
        ConcurrentHashMap<String, Long> concurrentHashMap = getData(ITEM_COUNT - 10);
        //初始900个元素
        log.info("init size:{}", concurrentHashMap.size());
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);//使用线程池并发处理逻辑
        //查询还需要补充多少个元素
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, 10).parallel().forEach(e -> {
            int gap = ITEM_COUNT - concurrentHashMap.size();
            log.info("gap size:{}", gap);//补充元素
            concurrentHashMap.putAll(getData(gap));
        }));//等待所有任务完成
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);//最后元素个数会是1000吗？
        log.info("finish size:{}", concurrentHashMap.size());
        return "OK";
    }

    private static Map<String, Long> normaluse() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ConcurrentHashMap<String, Long> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(e -> {
            String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
            synchronized (freqs) {
                if (freqs.containsKey(key)) {//Key存在则+1
                    freqs.put(key, freqs.get(key) + 1);
                } else {//Key不存在则初始化为1
                    freqs.put(key, 1L);
                }
            }
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        stopWatch.stop();
        log.info("normaluse耗时:{}s", stopWatch.getTotalTimeSeconds());
        return freqs;
    }

    private static Map<String, Long> gooduse() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(e -> {
            String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
            freqs.computeIfAbsent(key, k -> new LongAdder()).increment();
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        Map<String, Long> collect = freqs.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().longValue()));
        stopWatch.stop();
        log.info("gooduser耗时:{}s", stopWatch.getTotalTimeSeconds());
        return
                collect;
    }

    public static String good() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("normaluse");
        Map<String, Long> normaluse = normaluse();
        stopWatch.stop();
        //校验元素数量
        Assert.isTrue(normaluse.size() == ITEM_COUNT, "normaluse size error");//校验累计总数
        Assert.isTrue(normaluse.entrySet().stream().mapToLong(item -> item.getValue()).reduce(0, Long::sum) == LOOP_COUNT, "normaluse count error");
        stopWatch.start("gooduse");
        Map<String, Long> gooduse = gooduse();
        stopWatch.stop();
        Assert.isTrue(gooduse.size() == ITEM_COUNT, "gooduse size error");
        Assert.isTrue(gooduse.entrySet().stream().mapToLong(item -> item.getValue()).reduce(0, Long::sum) == LOOP_COUNT, "gooduse count error");
        log.info(stopWatch.prettyPrint());
        return "OK";
    }
}