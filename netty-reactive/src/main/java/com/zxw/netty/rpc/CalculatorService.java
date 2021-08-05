package com.zxw.netty.rpc;

import java.math.BigDecimal;

/**
 * @author zxw
 * @date 2021-04-20 20:24
 */
public interface CalculatorService {
    BigDecimal add(BigDecimal op1, BigDecimal op2);
    BigDecimal substract(BigDecimal op1, BigDecimal op2);
    BigDecimal multiply(BigDecimal op1, BigDecimal op2);
}
