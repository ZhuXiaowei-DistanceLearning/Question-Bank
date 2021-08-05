package designpattern.status.processor;


import designpattern.status.Checkable;
import designpattern.status.CreateOrderContext;
import designpattern.status.ServiceResult;
import designpattern.status.checker.Checker;
import designpattern.status.checker.CreateParamChecker;
import designpattern.status.checker.UnFinishChecker;
import designpattern.status.checker.UserChecker;
import designpattern.status.context.StateContext;
import designpattern.status.pluging.OrderStateEnum;
import designpattern.status.processor.annotaion.OrderProcessor;
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
    public ServiceResult<String, CreateOrderContext> check(StateContext<CreateOrderContext> context) {
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
    public ServiceResult<String, CreateOrderContext> action(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        return null;
    }

    @Override
    public ServiceResult<String, CreateOrderContext> save(String nextState, StateContext<CreateOrderContext> context) throws Exception {
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