package com.zxw.gateway;

/**
 * @author zxw
 * @date 2022/7/15 16:27
 */
public class GateWayBootstrap {
    public static void main(String[] args) {
        GateWayConnector gateWayConnector = new GateWayConnector();
        gateWayConnector.run();
    }
}
