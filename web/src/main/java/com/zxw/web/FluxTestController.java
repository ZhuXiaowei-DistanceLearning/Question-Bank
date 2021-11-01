package com.zxw.web;

import com.zxw.reactive.SampleSubscriber;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zxw
 * @date 2021/11/1 10:15
 */
@RequestMapping("/reactor")
public class FluxTestController {
    @GetMapping("/test1")
    public Flux<String> test1() {
        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
        SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
        Flux<Integer> ints = Flux.range(1, 4);
        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);
        Flux.range(1, 10).subscribe(
                System.out::println,
                error -> System.err.println("Error: " + error),
                () -> {
                    System.out.println("Done");
                },
                s -> ss.request(10));
        return seq1;
    }

    @GetMapping("/test2")
    public Flux<String> test2() {
        Flux<String> flux = Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next("3 x " + state + " = " + 3 * state);
                    if (state == 10) sink.complete();
                    return state + 1;
                });

        Flux<String> flux2 = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3 * i);
                    if (i == 10) sink.complete();
                    return state;
                }, (state) -> System.out.println("state: " + state));
        return flux;
    }
}
