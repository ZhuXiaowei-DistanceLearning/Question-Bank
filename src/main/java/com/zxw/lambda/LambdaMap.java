package com.zxw.lambda;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zxw
 * @date 2021-05-22 19:57
 */
public class LambdaMap {
    static Map<String, String> artistCache = new HashMap<>();

    public static void main(String[] args) {

        String test = artistCache.computeIfAbsent("test", (k) -> "qwe");
         artistCache.computeIfAbsent("test", (k) -> "asd");
        System.out.println(test);
        System.out.println(artistCache.get("test"));
    }
}
