package designpattern.status.pluging;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface ProcessorPlugin {
    /**
     * 指定状态，state不能同时存在
     */
    String[] state() default {};
    /**
     * 订单操作事件
     */
    String event();
    /**
     * 业务
     */
    String[] bizCode() default {};
    /**
     * 场景
     */
    String[] sceneId() default {};
}