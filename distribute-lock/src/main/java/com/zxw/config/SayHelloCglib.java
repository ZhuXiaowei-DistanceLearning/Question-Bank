package com.zxw.config;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author zxw
 * @date 2022/7/20 23:10
 */
public class SayHelloCglib implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz) {
        //设置需要创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        //通过字节码技术动态创建子类实例
        return enhancer.create();
    }

    public static void main(String[] args) {
        SayHelloCglib proxy = new SayHelloCglib();
        //通过生成子类的方式创建代理类
        SayServiceImpl proxyImp = (SayServiceImpl) proxy.getProxy(SayServiceImpl.class);
        proxyImp.say();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("前置代理");
        //通过代理类调用父类中的方法
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("后置代理");
        return result;
    }
}
