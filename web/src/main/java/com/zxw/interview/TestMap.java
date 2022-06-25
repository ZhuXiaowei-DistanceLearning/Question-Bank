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

    public static int lengthOfLongestSubstring(String s) {
        if(s.length() == 0){
            return 0;
        }
        int res = 0;
        int l = 0;
        int r = 0;
        Map<Character, Integer> map  = new HashMap();
        char[] c = s.toCharArray();
        while(r < c.length){
            if(map.containsKey(c[r])){
                l = Math.max(l ,map.get(c[r]) + 1);
            }
            res = Math.max(res, r - l + 1);
            map.put(c[r], r);
            r++;
        }
        return res;
    }

    public String longestPalindrome(String s) {
        String res = "";
        for(int i =0; i< s.length(); i++){
            String s1 = palindrome(s, i, i);
            String s2 = palindrome(s, i, i + 1);
            res = res.length() > s1.length() ? res : s1;
            res = res.length() > s2.length() ? res : s2;
        }
        return res;
    }

    /**
     * 本地的核心在于找到左边最高的位置与右边最高的位置，减去自身的位置就能得到当前位置所能装的雨水
     * @param s
     * @param l
     * @param r
     * @return
     */
    public String palindrome(String s, int l, int r){
        while(l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)){
            l--;
            r++;
        }
        return s.substring(l + 1, r);
    }

    int trap(int[] height) {
        int n = height.length;
        int res = 0;
        for (int i = 1; i < n - 1; i++) {
            int l_max = 0, r_max = 0;
            // 找右边最高的柱子
            for (int j = i; j < n; j++)
                r_max = Math.max(r_max, height[j]);
            // 找左边最高的柱子
            for (int j = i; j >= 0; j--)
                l_max = Math.max(l_max, height[j]);
            // 如果自己就是最高的话，
            // l_max == r_max == height[i]
            res += Math.min(l_max, r_max) - height[i];
        }
        return res;
    }

    @SneakyThrows
    public static void main(String[] args) {
        TestMap testMap = new TestMap();
        testMap.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1});
        testMap.longestPalindrome("babad");
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
