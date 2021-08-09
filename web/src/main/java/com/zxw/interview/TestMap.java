package com.zxw.interview;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zxw
 * @date 2020-12-26 21:28
 */
public class TestMap {
    private static HashMap<Object, Object> map = new HashMap();
    private static ConcurrentHashMap<Object, Object> conMap = new ConcurrentHashMap();

    public static void main(String[] args) {
        int i = "test02131012".hashCode();
        String num = "15";
        System.out.println(i);
        System.out.println((15 & i));
        System.out.println((15 & (i ^ (i>>> 16))));
        System.out.println(1&7);
        System.out.println(2&7);
        System.out.println(3&7);
        System.out.println(4&7);
        System.out.println(5&7);
        System.out.println(6&7);
        System.out.println(7&7);
        System.out.println(8&7);
        System.out.println(9&1231232);
        Set<Object> objects = map.keySet();
        Set<Map.Entry<Object, Object>> entries = map.entrySet();
        map.put(1, 1);
        conMap.put(1, 1);
    }
}
