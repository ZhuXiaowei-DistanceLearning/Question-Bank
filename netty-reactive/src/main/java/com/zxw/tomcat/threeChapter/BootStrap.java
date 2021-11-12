package com.zxw.tomcat.threeChapter;

/**
 * @author zxw
 * @date 2021/11/12 17:21
 */
public class BootStrap {
    public static void main(String[] args) {
        HttpConnector httpConnector = new HttpConnector();
        httpConnector.start();
    }
}
