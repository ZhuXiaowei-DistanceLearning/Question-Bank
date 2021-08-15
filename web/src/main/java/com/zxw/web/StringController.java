package com.zxw.web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
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
        return Flux.just(jsonObject.toJSONString(),"aaaa");
    }
    @GetMapping("/webClient")
    public void webClient() {
        WebClient webClient = WebClient.create("http://localhost:8081");   // 1
        Mono<String> resp = webClient
                .get().uri("/string") // 2
                .retrieve() // 3
                .bodyToMono(String.class);  // 4
        resp.subscribe(System.out::println);    // 5
    }
}
