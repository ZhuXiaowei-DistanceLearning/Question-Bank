package com.zxw.web;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxw
 * @date 2021-05-09 0:35
 */
@RequestMapping("/string")
@RestController
public class StringController {
    @GetMapping
    public String hello(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello","word");
        jsonObject.put("Language","chinese");
        return jsonObject.toJSONString();
    }
}
