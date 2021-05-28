package com.zxw.lambda;

/**
 * @author zxw
 * @date 2021-05-25 23:09
 */
public final class Expect {
    public BoundExpectation that(Object value) {
        return new BoundExpectation(value);
    }
}
