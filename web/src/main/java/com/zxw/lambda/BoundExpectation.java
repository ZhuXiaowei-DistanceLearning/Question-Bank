package com.zxw.lambda;

import lombok.Data;

/**
 * @author zxw
 * @date 2021-05-25 23:10
 */
@Data
public class BoundExpectation extends Exception {
    private Object value;

    public BoundExpectation(Object value) {
        super();
    }

}
