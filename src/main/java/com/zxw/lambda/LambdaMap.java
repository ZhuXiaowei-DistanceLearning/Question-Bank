package com.zxw.lambda;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zxw
 * @date 2021-05-22 19:57
 */
public class LambdaMap {
    static Map<String, String> artistCache = new HashMap<>();
    static Map<String, Map<String, String>> cache = new HashMap<>();

    static {
        HashMap<String, String> m1 = new HashMap<>();
        HashMap<String, String> m2 = new HashMap<>();
        HashMap<String, String> m3 = new HashMap<>();
        m1.put("1", "1");
        m2.put("2", "1");
        m3.put("1", "2");
        cache.put("1", m1);
        cache.put("2", m2);
        cache.put("1", m3);
    }

    public static void main(String[] args) {
        String test = artistCache.computeIfAbsent("test", (k) -> "qwe");
        artistCache.computeIfAbsent("test", (k) -> "asd");
        System.out.println(test);
        System.out.println(artistCache.get("test"));
    }
}
