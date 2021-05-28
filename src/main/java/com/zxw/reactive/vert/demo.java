package com.zxw.reactive.vert;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * @author zxw
 * @date 2021/5/28 15:53
 */
public class demo {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(MyFirstVerticle.class.getName());
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vert = res.result(); // 获取到了集群模式下的 Vertx 对象
                // 做一些其他的事情
            } else {
                // 获取失败，可能是集群管理器出现了问题
            }
        });
    }
}
