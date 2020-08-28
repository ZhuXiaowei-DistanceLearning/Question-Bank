package com.zxw.reactive;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author zxw
 * @date 2020/8/26 18:25
 */
public class MyProcessor extends SubmissionPublisher<String> implements Flow.Processor<Integer, String> {
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        // 保存订阅关系
        this.subscription = subscription;
        // 请求一个数据
        this.subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("处理接收的数据:" + item);
        if (item > 0) {
            this.submit("转换后的数据:" + item);
        }
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        // 通知发布者不接受数据
        this.subscription.cancel();
    }

    @Override
    public void onComplete() {
        System.out.println("数据处理完成");
        // 关闭发布者
        this.close();
    }
}
