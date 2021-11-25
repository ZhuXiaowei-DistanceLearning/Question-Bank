package com.zxw.designpattern.status.processor;

import com.zxw.designpattern.status.processor.annotaion.StateProcessor;
import com.zxw.designpattern.status.ServiceResult;
import com.zxw.designpattern.status.context.StateContext;

/**
 * 状态A对应的状态处理器
 */
public class StateAProcessor implements StateProcessor {
    @Override
    public ServiceResult action(StateContext context) throws Exception {
        return null;
    }
}