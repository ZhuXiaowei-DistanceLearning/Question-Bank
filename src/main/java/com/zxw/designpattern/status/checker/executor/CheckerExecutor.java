package com.zxw.designpattern.status.checker.executor;

import com.zxw.designpattern.status.ServiceResult;
import com.zxw.designpattern.status.checker.Checker;
import com.zxw.designpattern.status.context.StateContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 校验器的执行器
 */
@Slf4j
@Component
public class CheckerExecutor<T, C> {
    /**
     * 执行并行校验器，
     * 按照任务投递的顺序判断返回。
     */
    public ServiceResult parallelCheck(List<Checker> checkers, StateContext<C> context) {
        if (!CollectionUtils.isEmpty(checkers)) {
            if (checkers.size() == 1) {
                return checkers.get(0).check(context);
            }
            List<Future<ServiceResult>> resultList = Collections.synchronizedList(new ArrayList<>(checkers.size()));
            checkers.sort(Comparator.comparingInt(Checker::order));
            for (Checker c : checkers) {
                ExecutorService executor = Executors.newCachedThreadPool();
                Future<ServiceResult> future = executor.submit(() -> c.check(context));
                resultList.add(future);
            }
            for (Future<ServiceResult> future : resultList) {
                try {
                    ServiceResult sr = future.get();
                    if (!sr.isSuccess()) {
                        return sr;
                    }
                } catch (Exception e) {
                    log.error("parallelCheck executor.submit error.", e);
                    throw new RuntimeException(e);
                }
            }
        }
        return new ServiceResult<>();
    }
}