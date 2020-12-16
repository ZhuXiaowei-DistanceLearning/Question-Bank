package com.zxw.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author zxw
 * @date 2020-12-16 10:19
 */
public class MetaSpaceCreate {
    public static void main(String[] args) {
        int count = 1;
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Car.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o, objects);
                }
            });
            System.out.println(count);
            count++;
        }
    }

    static class Car {
        public void run() {
            System.out.println("汽车启动");
        }
    }
}