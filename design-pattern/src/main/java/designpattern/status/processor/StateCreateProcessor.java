package designpattern.status.processor;

import designpattern.status.Checkable;
import designpattern.status.CreateOrderContext;
import designpattern.status.processor.annotaion.OrderProcessor;
import designpattern.status.ServiceResult;
import designpattern.status.context.StateContext;

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
    public ServiceResult<String, CreateOrderContext> check(StateContext<CreateOrderContext> context) {
        return null;
    }

    @Override
    public String getNextState(StateContext<CreateOrderContext> context) {
        return null;
    }

    @Override
    public ServiceResult<String, CreateOrderContext> action(String nextState, StateContext<CreateOrderContext> context) throws Exception {
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