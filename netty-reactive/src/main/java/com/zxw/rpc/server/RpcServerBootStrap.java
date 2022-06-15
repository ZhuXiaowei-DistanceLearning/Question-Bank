package com.zxw.rpc.server;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zxw
 * @date 2022/6/15 14:38
 */
@Slf4j
public class RpcServerBootStrap {

    public static void main(String[] args) {
        RpcServerConnector connector = new RpcServerConnector();
        connector.run();
    }
}
