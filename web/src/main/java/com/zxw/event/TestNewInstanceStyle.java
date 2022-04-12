package com.zxw.event;

import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;

public class TestNewInstanceStyle {
    public static void main(String[] args) throws Exception {
        //ReflectionFactory.newConstructorForSerialization()方式
        ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();
        Constructor constructor = reflectionFactory.newConstructorForSerialization(TestObject.class);
        constructor.setAccessible(true);
        TestObject testObject1 = (TestObject) constructor.newInstance();
        System.out.println(testObject1.name);
        //普通方式
        TestObject testObject2 = new TestObject();
        System.out.println(testObject2.name);
    }

    public static class TestObject {
        public String name = "fujian";
    }
}