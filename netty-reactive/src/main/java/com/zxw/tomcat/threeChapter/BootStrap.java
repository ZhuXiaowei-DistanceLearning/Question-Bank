package com.zxw.tomcat.threeChapter;

import org.apache.catalina.LifecycleException;

/**
 * @author zxw
 * @date 2021/11/12 17:21
 */
public class BootStrap {
    public static void main(String[] args) throws LifecycleException {
        HttpConnector httpConnector = new HttpConnector();
        SimpleContainer simpleContainer = new SimpleContainer();
        httpConnector.setContainer(simpleContainer);
//        httpConnector.init();
//        httpConnector.start();
        httpConnector.begin();
    }
}
