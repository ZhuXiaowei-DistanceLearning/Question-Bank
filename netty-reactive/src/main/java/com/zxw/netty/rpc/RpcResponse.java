package com.zxw.netty.rpc;

import lombok.Data;

@Data
public class RpcResponse {

  // 请求ID
  private String requestId;

  // 错误信息
  private String error;

  // 响应信息
  private Object result;
}