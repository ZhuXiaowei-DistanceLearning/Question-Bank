package com.zxw.interview;

import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * @author zxw
 * @date 2020-12-26 21:28
 */
public class TestMap {
    private static Map<Object, Object> map = new HashMap();
    private static ConcurrentHashMap<Object, Object> conMap = new ConcurrentHashMap();

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("1");
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
        int i = "test02131012".hashCode();
        String num = "15";
        System.out.println(i);
        System.out.println((15 & i));
        System.out.println((15 & (i ^ (i >>> 16))));
        System.out.println(1 & 7);
        System.out.println(2 & 7);
        System.out.println(3 & 7);
        System.out.println(4 & 7);
        System.out.println(5 & 7);
        System.out.println(6 & 7);
        System.out.println(7 & 7);
        System.out.println(8 & 7);
        System.out.println(9 & 1231232);
        Set<Object> objects = map.keySet();
        Set<Map.Entry<Object, Object>> entries = map.entrySet();
        map.put(1, 1);
        conMap.put(1, 1);
    }
}
