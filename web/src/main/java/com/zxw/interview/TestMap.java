package com.zxw.interview;

import com.zxw.datastruct.ListNode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zxw
 * @date 2020-12-26 21:28
 */
@Slf4j
public class TestMap {
    private static final int ITEM_COUNT = 1000000;
    private static final int THREAD_COUNT = 2;
    private static final int LOOP_COUNT = 1000000;
    private static Map<Object, Object> map = new HashMap();
    private static ConcurrentHashMap<Object, Object> conMap = new ConcurrentHashMap();

    static ListNode res = new ListNode();
    static boolean add = false;

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1 == null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }
        ListNode res = new ListNode();
        ListNode cur = res;
        boolean add = false;
        while(l1!=null || l2 !=null){
            int n = l1 == null ? 0 : l1.val;
            int m = l2 == null ? 0 : l2.val;
            int addNum = add ? n + m + 1 : n + m;
            if(addNum >= 10){
                add = true;
                addNum = addNum%10;
            }else{
                add = false;
            }
            if(l1 != null){
                l1 = l1.next;
            }
            if(l2 != null){
                l2 = l2.next;
            }
            cur.next = new ListNode(addNum);
            cur = cur.next;
        }
        if(add){
            cur.next = new ListNode(1);
        }
        return res.next;
    }

    @SneakyThrows
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(4);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(5);
        ListNode l5 = new ListNode(6);
        ListNode l6 = new ListNode(4);
        l1.next = l2;
        l2.next = l3;
        l4.next = l5;
        l5.next = l6;
        ListNode node = l1;
        l1 = l1.next;
        addTwoNumbers(l1, l4);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("normaluse");
        Map<String, Long> normaluse = normaluse();
        stopWatch.stop();
        //校验元素数量
        Assert.isTrue(normaluse.size() == ITEM_COUNT, "normaluse size error");
        //校验累计总数
        Assert.isTrue(normaluse.entrySet().stream()
                        .mapToLong(item -> item.getValue()).reduce(0, Long::sum) == LOOP_COUNT
                , "normaluse count error");
        stopWatch.start("gooduse");
        Map<String, Long> gooduse = gooduse();
        stopWatch.stop();
        Assert.isTrue(gooduse.size() == ITEM_COUNT, "gooduse size error");
        Assert.isTrue(gooduse.entrySet().stream()
                        .mapToLong(item -> item.getValue())
                        .reduce(0, Long::sum) == LOOP_COUNT
                , "gooduse count error");
        log.info(stopWatch.prettyPrint());
    }

    private static void test1() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("1");
        // 判断Key是否存在Value，如果不存在则把Lambda表达式运行后的结果放入Map作为Value，也就是新创建一个LongAdder对象，最后返回Value。
        IntStream.range(1, 1000000)
                .forEach(e -> map.computeIfAbsent(e, k -> new TestMap()));
        stopWatch.stop();
        System.out.println(map.size());
        map.clear();
        stopWatch.start("2");
        IntStream.range(1, 1000000)
                .forEach(e -> map.putIfAbsent(e, new TestMap()));
        System.out.println();
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    private static Map<String, Long> normaluse() throws InterruptedException {
        ConcurrentHashMap<String, Long> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
                    //获得一个随机的Key
                    String key = String.valueOf(i);
                    synchronized (freqs) {
                        if (freqs.containsKey(key)) {
                            //Key存在则+1
                            freqs.put(key, freqs.get(key) + 1);
                        } else {
                            //Key不存在则初始化为1
                            freqs.put(key, 1L);
                        }
                    }
                }
        ));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return freqs;
    }

    private static Map<String, Long> gooduse() throws InterruptedException {
        ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
                    freqs.computeIfAbsent(String.valueOf(i), k -> new LongAdder()).increment();
                }
        ));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        //因为我们的Value是LongAdder而不是Long，所以需要做一次转换才能返回
        return freqs.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().longValue())
                );
    }

}
