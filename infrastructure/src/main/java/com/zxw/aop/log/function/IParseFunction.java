package com.zxw.aop.log.function;

public interface IParseFunction {

    default boolean executeBefore() {
        return false;
    }

    String functionName();

    String apply(String value);
}