package com.zxw.ribbon;

import com.google.common.collect.Lists;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * @author zxw
 * @date 2022-01-23 12:59
 */
public class RibbonDemo {
    public static void main(String[] args) {
        ArrayList<Server> serverList = Lists.newArrayList(
                new Server("localhost", 8081),
                new Server("localhost", 8082));
        ILoadBalancer loadBalancer = LoadBalancerBuilder.newBuilder().buildFixedServerListLoadBalancer(serverList);
        IntStream.range(1, 5)
                .forEach(e -> {
                    LoadBalancerCommand<Object> command = LoadBalancerCommand.builder()
                            .withLoadBalancer(loadBalancer)
                            .build();
                });
    }
}
