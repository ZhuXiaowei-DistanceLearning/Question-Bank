package designpattern.status.processor;

import designpattern.status.ServiceResult;
import designpattern.status.context.StateContext;
import designpattern.status.processor.annotaion.StateProcessor;

/**
 * 状态A对应的状态处理器
 */
public class StateAProcessor implements StateProcessor {
    @Override
    public ServiceResult action(StateContext context) throws Exception {
        return null;
    }
}