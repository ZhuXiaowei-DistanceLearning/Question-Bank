package com.zxw.utils;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

/**
 * @author zxw
 * @date 2022/3/11 16:20
 */
public class GroovyUtils {
    public static void main(String[] args) {
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class groovyClass = groovyClassLoader.parseClass("def cal(int a, int b){\n" +
                "    return a+b\n" +
                "}");
        try {
            Object[] param = {8, 7};
            GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
            int result = (int) groovyObject.invokeMethod("cal", param);
            System.out.println(result);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
