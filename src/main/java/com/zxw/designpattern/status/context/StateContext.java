package com.zxw.designpattern.status.context;

import lombok.Data;

/**
 * @author zxw
 * @date 2021/5/20 16:37
 */
@Data
public class StateContext<C> {
    /**
     * 订单操作事件
     */
    private OrderStateEvent orderStateEvent;
    /**
     * 状态机需要的订单基本信息
     */
    private FsmOrder fsmOrder;
    /**
     * 业务可定义的上下文泛型对象
     */
    private C context;
    public StateContext(OrderStateEvent orderStateEvent, FsmOrder fsmOrder) {
        this.orderStateEvent = orderStateEvent;
        this.fsmOrder = fsmOrder;
    }
}
