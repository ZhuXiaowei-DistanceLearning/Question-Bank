package com.zxw.netty.rpc;

import java.math.BigDecimal;

/**
 * @author zxw
 * @date 2021-04-20 20:25
 */
public class CalculatorServiceImpl implements CalculatorService{
    @Override
    public BigDecimal add(BigDecimal op1, BigDecimal op2) {
        return op1.add(op2);
    }

    @Override
    public BigDecimal substract(BigDecimal op1, BigDecimal op2) {
        return op1.subtract(op2);
    }

    @Override
    public BigDecimal multiply(BigDecimal op1, BigDecimal op2) {
        return op1.multiply(op2);
    }
}
