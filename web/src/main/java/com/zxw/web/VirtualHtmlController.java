package com.zxw.web;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zxw
 * @date 2021/8/31 9:06
 */
@RequestMapping("/virtual/view")
public class VirtualHtmlController {

    @GetMapping("/my")
    public String my(ModelMap map) {
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
        map.put("res", res);
        return "/phone";
    }
}
