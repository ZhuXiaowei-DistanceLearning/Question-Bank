package com.zxw.aop.log;

import com.zxw.vo.base.Result;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author zxw
 * @date 2022/6/7 22:53
 */
public class LogRecordConfigureSelector {

    public static void main(String[] args) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("#root.code");
        Result order = new Result();
        order.setCode("张三");
        System.out.println(expression.getValue(order));
    }
}
