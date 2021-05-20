package com.zxw.designpattern.status.processor;

import com.zxw.designpattern.status.Checkable;
import com.zxw.designpattern.status.CreateOrderContext;
import com.zxw.designpattern.status.processor.annotaion.OrderProcessor;
import com.zxw.designpattern.status.ServiceResult;
import com.zxw.designpattern.status.context.StateContext;

/**
 * 创建订单状态对应的状态处理器
 */
@OrderProcessor(state = "INIT", bizCode = {"CHEAP","POPULAR"}, sceneId = "H5")
public class StateCreateProcessor extends AbstractStateProcessor<String, CreateOrderContext> {
    @Override
    public void prepare(StateContext<CreateOrderContext> context) {
        super.prepare(context);
    }

    @Override
    public ServiceResult<String> check(StateContext<CreateOrderContext> context) {
        return null;
    }

    @Override
    public String getNextState(StateContext<CreateOrderContext> context) {
        return null;
    }

    @Override
    public ServiceResult<String> action(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        return null;
    }

    @Override
    public ServiceResult save(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        return null;
    }

    @Override
    public void after(StateContext<CreateOrderContext> context) {

    }

    @Override
    public Checkable getCheckable(StateContext<CreateOrderContext> context) {
        return null;
    }

}