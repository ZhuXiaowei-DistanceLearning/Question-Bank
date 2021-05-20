package com.zxw.designpattern.status.processor;

import com.zxw.designpattern.status.*;
import com.zxw.designpattern.status.checker.Checker;
import com.zxw.designpattern.status.checker.CreateParamChecker;
import com.zxw.designpattern.status.checker.UnFinishChecker;
import com.zxw.designpattern.status.checker.UserChecker;
import com.zxw.designpattern.status.context.StateContext;
import com.zxw.designpattern.status.pluging.OrderStateEnum;
import com.zxw.designpattern.status.processor.annotaion.OrderProcessor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@OrderProcessor(state = "INIT", bizCode = {"CHEAP", "POPULAR"}, sceneId = "H5")
@Slf4j
public class OrderCreatedProcessor extends AbstractStateProcessor<String, CreateOrderContext> {
    @Resource
    private CreateParamChecker createParamChecker;
    @Resource
    private UserChecker userChecker;
    @Resource
    private UnFinishChecker unfinshChecker;

    @Override
    public void prepare(StateContext<CreateOrderContext> context) {
        super.prepare(context);
    }

    @Override
    public ServiceResult<String, String> check(StateContext<CreateOrderContext> context) {
        return null;
    }

    @Override
    public String getNextState(StateContext<CreateOrderContext> context) {
        // if (context.getOrderStateEvent().getEventType().equals("xxx")) {
        //     return OrderStateEnum.INIT;
        //  }
        return OrderStateEnum.NEW;
    }

    @Override
    public ServiceResult<String, String> action(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        return null;
    }

    @Override
    public ServiceResult<String, String> save(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        OrderInfo orderInfo = context.getContext().getOrderInfo();
        // 更新状态
        orderInfo.setOrderState(nextState);
        // 持久化
//        this.updateOrderInfo(orderInfo);
        log.info("save BUSINESS order success, userId:{}, orderId:{}", orderInfo.getUserId(), orderInfo.getOrderId());
        return new ServiceResult(orderInfo.getOrderId(), "business下单成功");
    }

    @Override
    public void after(StateContext<CreateOrderContext> context) {

    }

    @Override
    public Checkable getCheckable(StateContext<CreateOrderContext> context) {
        return new Checkable() {
            @Override
            public List<Checker> getParamChecker() {
                return Arrays.asList(createParamChecker);
            }

            @Override
            public List<Checker> getSyncChecker() {
                return Collections.EMPTY_LIST;
            }

            @Override
            public List<Checker> getAsyncChecker() {
                return Arrays.asList(userChecker, unfinshChecker);
            }
        };
    }
}