package com.zxw.designpattern.status;

import com.zxw.designpattern.status.processor.OrderInfo;
import lombok.Data;

/**
 * @author zxw
 * @date 2021/5/20 16:37
 */
@Data
public class CreateOrderContext {
    private String estimatePriceInfo;
    private OrderInfo orderInfo;
}
