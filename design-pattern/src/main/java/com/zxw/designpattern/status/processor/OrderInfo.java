package com.zxw.designpattern.status.processor;

import lombok.Data;

/**
 * @author zxw
 * @date 2021/5/20 17:02
 */
@Data
public class OrderInfo {
    private String orderState;
    private String userId;
    private String orderId;
}
