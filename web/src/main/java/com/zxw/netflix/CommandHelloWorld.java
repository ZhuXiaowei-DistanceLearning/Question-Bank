package com.zxw.netflix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;

    public CommandHelloWorld(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionTimeoutEnabled(true)
                                .withExecutionIsolationThreadInterruptOnTimeout(true)
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                                .withExecutionTimeoutInMilliseconds(10)
                                .withFallbackEnabled(true))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withCoreSize(1)
                                .withMaximumSize(2)
                                .withMaxQueueSize(10)
                ));
        this.name = name;
    }

    @SneakyThrows
    @Override
    protected String run() {
        // a real example would do work like a network call here
        log.info("执行run");
        Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
        return "Hello " + name + "!";
    }

    @SneakyThrows
    public static void main(String[] args) {
//        Future<String> s1 = new CommandHelloWorld("Bob").queue();
//        log.info("{}", s1.get());
//        Observable<String> s2 = new CommandHelloWorld("Bob").observe();
//        log.info("{}", s2.first());
        CommandHelloWorld commandHelloWorld = new CommandHelloWorld("Bob");
        EventLoopGroup group = new DefaultEventLoopGroup(2);
        while (true) {
            group.execute(() -> {
                try {
                    String s = new CommandHelloWorld("Bob").execute();
                    log.info("{}", s);
                } catch (HystrixRuntimeException e) {
                    log.error("服务熔断");
                }
            });
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
        }
    }
}

