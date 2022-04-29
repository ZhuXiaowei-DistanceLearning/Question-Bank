package com.zxw.web;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxw.web.po.ProductBasicInfo;
import com.zxw.web.service.DeviceBasicInfoService;
import com.zxw.web.service.ProductBasicInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxw
 * @date 2022/4/28 22:30
 */
@RequestMapping("/aiot")
@RestController
@Slf4j
public class AiotController {

    @Autowired
    private ProductBasicInfoService productBasicInfoService;

    @Autowired
    private DeviceBasicInfoService deviceBasicInfoService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private String prefix = "/aiot/";

    @GetMapping("/init")
    public void init() {
        int page = 1;
        while (true) {
            Page<ProductBasicInfo> productBasicInfoPage = productBasicInfoService.page(new Page<>(page, 1000));
            if (productBasicInfoPage.getRecords().isEmpty()) {
                break;
            }
            page++;
            productBasicInfoPage.getRecords().forEach(e -> {
                redisTemplate.opsForValue().set(prefix + "product_" + e.getProdId(), JSON.toJSONString(e));
            });
        }
    }

}
