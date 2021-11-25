package com.zxw.designpattern.status.pluging;

import com.zxw.designpattern.status.processor.PluginHandler;
import com.zxw.designpattern.status.CreateOrderContext;
import com.zxw.designpattern.status.ServiceResult;
import com.zxw.designpattern.status.context.StateContext;

/**
 * 预估价插件
 */
@ProcessorPlugin(state = OrderStateEnum.INIT, event = OrderEventEnum.CREATE, bizCode = "BUSINESS")
public class EstimatePricePlugin implements PluginHandler<String, CreateOrderContext> {
    @Override
    public ServiceResult action(StateContext<CreateOrderContext> context) throws Exception {
//        String price = priceSerive.getPrice();
        String price = "";
        context.getContext().setEstimatePriceInfo(price);
        return new ServiceResult();
    }
}