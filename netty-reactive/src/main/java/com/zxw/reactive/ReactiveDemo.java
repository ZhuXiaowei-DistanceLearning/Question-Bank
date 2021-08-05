package com.zxw.reactive;


import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author zxw
 * @date 2021/5/28 16:48
 */
public class ReactiveDemo {
    public static void main(String[] args) throws Throwable {
        test01();
        test02();
    }

    private static void test03(){
//        Processor<Integer, Integer> p = RingBufferProcessor.create("test", 32); //*1
//        Stream<Integer> s = Streams.wrap(p); //*2
//        s.consume(i -> System.out.println(Thread.currentThread() + " data=" + i)); //*3
//        s.consume(i -> System.out.println(Thread.currentThread() + " data=" + i)); //*4
//        s.consume(i -> System.out.println(Thread.currentThread() + " data=" + i)); //*5
//        input.subscribe(p); //*6
    }

    private static void test02() throws Throwable {
        Consumer<String> consumer = value -> System.out.println(value);
        Function<Integer, String> transformation = integer -> "" + integer;
        Supplier<Integer> supplier = () -> 123;
        Consumer<Tuple2<Consumer<String>, String>> biConsumer = tuple -> {
            for (int i = 0; i < 10; i++) {
                // 类型正确，开启编译器
                tuple._1().accept(tuple._2());
            }
        };
        biConsumer.accept(
                Tuple.of(
                        consumer,
                        transformation.apply(supplier.get())
                )
        );
    }

    private static void test01() throws Throwable {
        Consumer<String> consumer = value -> System.out.println(value);

// 为了简约，现在用 Java 8 风格
        Function<Integer, String> transformation = integer -> "" + integer;

        Supplier<Integer> supplier = () -> 123;

        BiConsumer<Consumer<String>, String> biConsumer = (callback, value) -> {
            for (int i = 0; i < 10; i++) {
                // 对要运行的最后逻辑运行做惰性求值
                callback.accept(value);
            }
        };

// 注意生产者到双向消费者执行过程
        biConsumer.accept(
                consumer,
                transformation.apply(
                        supplier.get()
                )
        );
    }
}
