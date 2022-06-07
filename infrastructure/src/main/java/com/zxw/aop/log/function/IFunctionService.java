package com.zxw.aop.log.function;

/**
 * @author zxw
 * @date 2022/6/7 22:55
 */
public interface IFunctionService {
    String apply(String functionName, String value);

    boolean beforeFunction(String functionName);
}
