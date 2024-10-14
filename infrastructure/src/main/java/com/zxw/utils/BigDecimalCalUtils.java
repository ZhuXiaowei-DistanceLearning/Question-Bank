package com.zxw.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zxw
 * @date 2024-01-12 16:18
 */
public final class BigDecimalCalUtils {

    public static BigDecimal mul(BigDecimal number, BigDecimal... divNums) {
        if (number == null) {
            return null;
        }
        BigDecimal res = number;
        for (BigDecimal divNum : divNums) {
            res = res.multiply(divNum);
        }
        return res;
    }

    public static BigDecimal mulIfNull(BigDecimal number, BigDecimal defaultValue, BigDecimal... divNums) {
        if (number == null) {
            return defaultValue;
        }
        BigDecimal res = number;
        for (BigDecimal divNum : divNums) {
            res = res.multiply(divNum);
        }
        return res;
    }

    public static BigDecimal div(BigDecimal number, BigDecimal... divNums) {
        BigDecimal res = number;
        for (BigDecimal divNum : divNums) {
            res = res.divide(divNum, RoundingMode.HALF_DOWN);
        }
        return res;
    }

    public static BigDecimal div(BigDecimal number, int scale, BigDecimal... divNums) {
        BigDecimal res = number;
        for (BigDecimal divNum : divNums) {
            res = res.divide(divNum, scale, RoundingMode.HALF_DOWN);
        }
        return res.setScale(scale, RoundingMode.HALF_DOWN);
    }


    public static BigDecimal div(BigDecimal number, int scale, RoundingMode roundingMode, BigDecimal... divNums) {
        BigDecimal res = number;
        for (BigDecimal divNum : divNums) {
            if (divNum != null && divNum.compareTo(BigDecimal.ZERO) != 0) {
                res = res.divide(divNum, scale, roundingMode);
            }
        }
        return res.setScale(scale, roundingMode);
    }

    public static BigDecimal formatObject(Object num) {
        if (num == null) {
            return null;
        }
        String val = String.valueOf(num);
        if (StringUtils.equalsAny(val, "null", "-")) {
            return null;
        }
        return new BigDecimal(val);
    }
}
