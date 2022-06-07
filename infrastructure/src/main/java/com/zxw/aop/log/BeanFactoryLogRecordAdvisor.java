package com.zxw.aop.log;

import lombok.Data;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * @author zxw
 * @date 2022/6/7 22:31
 */
@Data
public class BeanFactoryLogRecordAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    LogRecordOperationSource logRecordOperationSource;

    @Override
    public Pointcut getPointcut() {
        return null;
    }

}