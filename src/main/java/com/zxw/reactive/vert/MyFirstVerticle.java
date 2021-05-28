package com.zxw.reactive.vert;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/**
 * @author zxw
 * @date 2021/5/28 16:08
 */
public class MyFirstVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
           req.response()
                   .putHeader("content-type","text/plain")
                   .end("hello word");
        }).listen(8080);
    }
}
