package com.zxw.web;

import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zxw
 * @date 2021/8/31 9:06
 */
@RequestMapping("/virtual/view")
@Controller
public class VirtualHtmlController {

//    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;

    @GetMapping("/my")
    public Mono<String> my(Model model) {
        List<String> res = new ArrayList<>();
        LocalDate now = LocalDate.now();
        String basic = "1" + String.valueOf(now.getMonth().ordinal() + 1) + String.valueOf(now.getDayOfMonth());
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                StringBuilder sb = new StringBuilder(basic);
                if (i == 0) {
                    sb.append("0000");
                } else {
                    sb.append((i * 1111));
                }
                if (j == 0) {
                    sb.append("000");
                } else {
                    sb.append((j * 111));
                }
                res.add(sb.toString());
            }
        }
        model.addAttribute("res", res);
        return Mono.create(monoSink -> monoSink.success("phone"));
    }

    @GetMapping("/hello")
    public Mono<String> hello(final Model model) {
        model.addAttribute("name", "泥瓦匠");
        model.addAttribute("city", "浙江温岭");

        String path = "test";
        return Mono.create(monoSink -> monoSink.success(path));
    }
}
