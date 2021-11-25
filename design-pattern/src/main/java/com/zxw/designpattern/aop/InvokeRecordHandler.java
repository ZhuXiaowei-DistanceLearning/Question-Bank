package com.zxw.designpattern.aop;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @author zxw
 * @date 2021-01-20 22:37
 */
public class InvokeRecordHandler extends BaseMethodAdviceHandler<Object> {
    /**
     * 记录方法出入参和调用时长
     */
    @Override
    public void onComplete(ProceedingJoinPoint point, long startTime, boolean permitted, boolean thrown, Object result) {
        String methodDesc = getMethodDesc(point);
        Object[] args = point.getArgs();
        long costTime = System.currentTimeMillis() - startTime;

        logger.warn("\n{} 执行结束，耗时={}ms，入参={}, 出参={}",
                methodDesc, costTime);
    }

    @Override
    protected String getMethodDesc(ProceedingJoinPoint point) {
        Method targetMethod = getTargetMethod(point);
        // 获得方法上的 InvokeRecordAnno
        InvokeRecordAnno anno = targetMethod.getAnnotation(InvokeRecordAnno.class);
        String description = anno.value();

        // 如果没有指定方法说明，那么使用默认的方法说明
        if (StringUtils.isBlank(description)) {
            description = super.getMethodDesc(point);
        }

        return description;
    }
}
