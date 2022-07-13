package com.zxw.event.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author zxw
 * @date 2022/7/12 14:44
 */
public class HelloEventFactory implements EventFactory<DisruptorModel> {
    @Override
    public DisruptorModel newInstance() {
        return new DisruptorModel();
    }
}
