package com.zxw.designpattern.status.processor;

import com.zxw.designpattern.status.processor.annotaion.StateProcessor;
import com.zxw.designpattern.status.Checkable;
import com.zxw.designpattern.status.ServiceResult;
import com.zxw.designpattern.status.StateActionStep;
import com.zxw.designpattern.status.checker.executor.CheckerExecutor;
import com.zxw.designpattern.status.checker.executor.PlugingExecutor;
import com.zxw.designpattern.status.context.StateContext;

import javax.annotation.Resource;

/**
 * 状态机处理器模板类
 */
//@Component
public abstract class AbstractStateProcessor<T, C> implements StateProcessor<T, C>, StateActionStep<T, C> {
    @Resource
    private CheckerExecutor checkerExecutor;
//    @Resource
    Checkable checkable;
    @Resource
    private PlugingExecutor pluginExecutor;

    @Override
    public final ServiceResult<T,C> action(StateContext<C> context) throws Exception {
        ServiceResult<T,C> result = null;
        try {
            // 参数校验器
            result = checkerExecutor.parallelCheck(checkable.getParamChecker(), context);
            if (!result.isSuccess()) {
                return result;
            }
            // 数据准备
            this.prepare(context);
            // 串行校验器
            result = this.check(context);
            if (!result.isSuccess()) {
                return result;
            }
            // getNextState不能在prepare前，因为有的nextState是根据prepare中的数据转换而来
            String nextState = this.getNextState(context);
            // 业务逻辑
            result = this.action(nextState, context);
            if (!result.isSuccess()) {
                return result;
            }
            // 在action和save之间执行插件逻辑
            this.pluginExecutor.parallelExecutor(context);
            // 持久化
            result = this.save(nextState, context);
            if (!result.isSuccess()) {
                return result;
            }
            // after
            this.after(context);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
}