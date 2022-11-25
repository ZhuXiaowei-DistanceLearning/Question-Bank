package com.zxw.learn.web;

import com.zxw.learn.annotation.Limit;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zxw
 * @date 2020/6/28 9:48
 */
@Controller
public class TestController {

    @GetMapping("/test")
    @Limit(count = 10)
    public void test(HttpServletRequest request){
    }
}
