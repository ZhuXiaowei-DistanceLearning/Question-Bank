package designpattern.status.pluging;

import designpattern.status.CreateOrderContext;
import designpattern.status.ServiceResult;
import designpattern.status.context.StateContext;
import designpattern.status.processor.PluginHandler;

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