package com.zxw.web;

import com.zxw.vo.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxw
 * @date 2022/6/28 20:58
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public Result test() {
        return Result.success("成功");
    }
}
