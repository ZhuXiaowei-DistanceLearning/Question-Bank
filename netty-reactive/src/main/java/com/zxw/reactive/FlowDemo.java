package com.zxw.reactive;


import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author zxw
 * @date 2020/8/26 18:42
 */
public class FlowDemo {
    public static void main(String[] args) {
        // 1. 定义发布者, 发布的数据类型是 Integer
        // 直接使用jdk自带的SubmissionPublisher
        SubmissionPublisher<Integer> publiser = new SubmissionPublisher<Integer>();

        // 2. 定义处理器, 对数据进行过滤, 并转换为String类型
        MyProcessor processor = new MyProcessor();

        // 3. 发布者 和 处理器 建立订阅关系
        publiser.subscribe(processor);

        // 4. 定义最终订阅者, 消费 String 类型数据
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<String>() {

            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                // 保存订阅关系, 需要用它来给发布者响应
                this.subscription = subscription;

                // 请求一个数据
                this.subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                // 接受到一个数据, 处理
                System.out.println("接受到数据: " + item);

                // 处理完调用request再请求一个数据
                this.subscription.request(1);

                // 或者 已经达到了目标, 调用cancel告诉发布者不再接受数据了
                // this.subscription.cancel();
            }

            @Override
            public void onError(Throwable throwable) {
                // 出现了异常(例如处理数据的时候产生了异常)
                throwable.printStackTrace();

                // 我们可以告诉发布者, 后面不接受数据了
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                // 全部数据处理完了(发布者关闭了)
                System.out.println("处理完了!");
            }
        };

        // 5. 处理器 和 最终订阅者 建立订阅关系
        processor.subscribe(subscriber);

        // 6. 生产数据, 并发布
        publiser.submit(-111);
        publiser.submit(111);

        // 7. 结束后 关闭发布者
        // 正式环境 应该放 finally 或者使用 try-resouce 确保关闭
        publiser.close();

        // 主线程延迟停止, 否则数据没有消费就退出
        try {
            Thread.currentThread().join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
