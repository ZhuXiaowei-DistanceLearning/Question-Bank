package com.zxw.utils;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Spring Expression Language
 * @author zxw
 * @date 2022/6/9 10:18
 */
public class SpELUtils {
    static SpelExpressionParser parser = new SpelExpressionParser();
    static Expression expression = null;

    static StandardEvaluationContext ctx = new StandardEvaluationContext();

    public static void main(String[] args) {
        ctx.setVariable("a", "b");
        ctx.setVariable("array", new int[5]);
        ctx.setVariable("collection", new ArrayList<>());
//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
//        test6();
//        test7();
        test8();
//        test9();
//        test10();
        // list?.length
        // 算数表达式（“1+2-3*4/2″）
        //比较表达式（“1>2”）
        //逻辑表达式（“2>1 and (!true or !false)”）
        //赋值表达式（“#variableName=value”）
        //三目表达式（“表达式1?表达式2:表达式3”）
        //正则表达式（“123′ matches ‘\\d{3}”）
        //            等运算符，均可以直接放在SpEL中
    }

    public static void test1() {
        expression = parser.parseExpression("#{'Hello World'}");
        System.out.println(expression.getValue());
    }

    public static void test2() {
        expression = parser.parseExpression("new String('Hello World')");
        System.out.println(expression.getValue());
    }

    public static void test3() {
        expression = parser.parseExpression("T(Integer).MAX_VALUE");
        System.out.println(expression.getValue());
    }

    public static void test4() {
        //  #this 使用当前正在计算的上下文
        //  #root 引用容器的root对象
        String result2 = parser.parseExpression("#root").getValue(ctx, String.class);
        System.out.println(result2);
        ctx.setVariable("abc", "abcdef");
        //取id为abc的bean，而后调用其中的substring方法
        System.out.println(parser.parseExpression("#abc.substring(0,1)").getValue(ctx, String.class));
    }

    public static void test5() {
        Method parseInt = null;
        try {
            parseInt = Integer.class.getDeclaredMethod("parseInt", String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        ctx.registerFunction("parseInt", parseInt);
        ctx.setVariable("parseInt2", parseInt);
        System.out.println(parser.parseExpression("#parseInt('3') == #parseInt2('3')").getValue(ctx));
    }

    public static void test6() {
        // 算数表达式（“1+2-3*4/2″）
        //比较表达式（“1>2”）
        //逻辑表达式（“2>1 and (!true or !false)”）
        //赋值表达式（“#variableName=value”）
        //三目表达式（“表达式1?表达式2:表达式3”）
        //正则表达式（“123′ matches ‘\\d{3}”）
        //等运算符，均可以直接放在SpEL中
    }

    public static void test7() {
        // name != null? name : "other"简写为： name?:"other"
        System.out.println(parser.parseExpression("#b?:'qwe'").getValue(ctx, String.class));
        System.out.println(parser.parseExpression("#a?.length").getValue(ctx, String.class));
    }

    public static void test8() {
        // 使用“{表达式，……}”定义List，如“{1,2,3}”
        //  对于字面量表达式列表，SpEL会使用java.util.Collections.unmodifiableList 方法将列表设置为不可修改。
        System.out.println(parser.parseExpression("{1,2,3}").getValue(List.class));
        //赋值语句
        System.out.println(parser.parseExpression("#array[1] = 3").getValue(ctx, int.class));
        //serValue方法
        System.out.println(parser.parseExpression("#array[2]").getValue(ctx, int.class));
        parser.parseExpression("#array[2]").setValue(ctx, 4);
        System.out.println(parser.parseExpression("#array[2]").getValue(ctx, int.class));
        //  语法：“(list|map).?[选择表达式]” 选择表达式结果必须是boolean类型，若是true则选择的元素将添加到新集合中，false将不添加到新集合中。
        System.out.println(parser.parseExpression("#collection.?[#this>2]").getValue(ctx, Collection.class));
    }

    public static void test9() {
        // //从readList的list下筛选出favorite为true的子集合，再将他们的name字段投为新的list
        //	@Value("#{list.?[favorite eq true].![name]}")
    }
}
