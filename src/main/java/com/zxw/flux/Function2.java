package com.zxw.flux;

/**
 * @author zxw
 * @date 2020-12-15 17:15
 */
@FunctionalInterface
public interface Function2<T, U, R> {
    void run(T scopt,R params);
}
