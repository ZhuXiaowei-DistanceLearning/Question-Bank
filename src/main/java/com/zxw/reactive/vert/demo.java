package com.zxw.reactive.vert;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.beetl.android.util.Log;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Observable;
import java.util.Observer;

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

    public void test1(){
        String tag ="";
        // 观察者,它决定事件触发的时候将有怎样的行为。
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
            }

            @Override
            public void onNext(String s) {
                Log.d(tag, "Item: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(tag, "Error!");
            }

            @Override
            public void onComplete() {

            }
        };
        // 被观察者，它决定什么时候触发事件以及触发怎样的事件。
        // 创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了。代码形式很简单：
        Publisher publisher = new Publisher() {
            @Override
            public void subscribe(Subscriber s) {

            }
        };



    }
}
