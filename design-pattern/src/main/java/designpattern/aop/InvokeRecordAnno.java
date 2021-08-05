package designpattern.aop;

/**
 * @author zxw
 * @date 2021-01-20 22:36
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于产生调用记录的注解，会记录下方法的出入参、调用时长
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InvokeRecordAnno {
    /**
     * 调用说明
     */
    String value() default "";
}
