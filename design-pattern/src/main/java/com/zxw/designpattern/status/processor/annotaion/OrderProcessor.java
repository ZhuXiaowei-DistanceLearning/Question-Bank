package com.zxw.designpattern.status.processor.annotaion;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 多状态、多类型、多场景、多维度
 * 状态机引擎的处理器注解标识
 * 状态机模式简单说就是：基于某些特定业务和场景下，根据源状态和发生的事件，来执行下一步的流程处理逻辑，并设置一个目标状态。
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface OrderProcessor {
    /**
     * 指定状态，state不能同时存在
     */
    String[] state() default {};
    /**
     * 业务
     */
    String[] bizCode() default {};
    /**
     * 场景
     */
    String[] sceneId() default {};
}