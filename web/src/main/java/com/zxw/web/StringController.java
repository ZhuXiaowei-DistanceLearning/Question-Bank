package com.zxw.web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * 一个Flux对象代表一个包含0…N个元素的响应式序列
 * 一个Mono对象代表一个包含零/一个（0…1）元素的结果。
 * @author zxw
 * @date 2021-05-09 0:35
 */
@RequestMapping("/string")
@RestController
public class StringController {
    @GetMapping
    public Mono<String> hello() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello", "word");
        jsonObject.put("Language", "chinese");
        return Mono.just(jsonObject.toJSONString());
    }

    @GetMapping("/list")
    public Flux<String> list() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello", "word");
        jsonObject.put("Language", "chinese");
        Flux.just(1,2,3,4,5,6).subscribe(System.out::println,System.out::println,()-> System.out.println("Completed"));
        return Flux.just(jsonObject.toJSONString(), "aaaa");
    }

    @GetMapping("/webClient")
    public void webClient() {
        WebClient webClient = WebClient.create("http://localhost:8081");   // 1
        Mono<String> resp = webClient
                .get().uri("/mq/sendMessage?handlerName=kafka") // 2
                .retrieve() // 3
                .bodyToMono(String.class);  // 4
        resp.subscribe(System.out::println);    // 5
    }

    @GetMapping("/intervalFlux")
    public void intervalFlux() {
        Flux.interval(Duration.ofSeconds(1))
                .doOnSubscribe(e -> {
                    WebClient.create("http://localhost:8081")   // 1
                            .get().uri("/mq/sendMessage?handlerName=kafka") // 2
                            .retrieve() // 3
                            .bodyToMono(String.class);
                }).share();  // 4)
    }
}
