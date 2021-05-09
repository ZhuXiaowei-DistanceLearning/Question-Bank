package com.zxw.netty.rpc;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author zxw
 * @date 2021-04-20 20:35
 */
@Data
@ToString
public class RpcResponse {
    private String id;
    private String serviceName;
    private String methodName;
    private String cause;
    private BigDecimal result;

    public RpcResponse() {
    }
}
