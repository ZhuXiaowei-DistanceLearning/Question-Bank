package com.zxw.learn.aop;

import com.zxw.learn.annotation.Limit;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zxw
 * @date 2020/6/28 9:58
 */
@Aspect
@Component
public class LimitAOP {
    private static final Logger logger = LoggerFactory.getLogger(LimitAOP.class);
    public static Queue<Integer> queue = new LinkedBlockingQueue<>();

    /**
     * @param joinpoint
     * @param limit
     */
    @Before("within(@org.springframework.stereotype.Controller *) && @annotation(limit)")
    public void requestLimit(final JoinPoint joinpoint, Limit limit) throws Exception {
        Object[] args = joinpoint.getArgs();
        HttpServletRequest request = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletRequest) {
                request = (HttpServletRequest) args[i];
                break;
            }
        }
        if (request == null) {
            throw new RuntimeException("缺失HttpServletRequest参数");
        }
        String ip = request.getHeader("X-Forwarded-For");
        Signature signature = joinpoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        queue.add(1);
        if (queue.size() > limit.count()) {
           logger.info("用户IP[" + ip + "]访问接口[" + methodName + "]超过了限定的次数[" + limit.count() + "]");
        } else {
            Thread.sleep(500);
            logger.info("当前该接口访问人数为:" + queue.size());
        }
        queue.poll();
    }
}
