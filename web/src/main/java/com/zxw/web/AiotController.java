package com.zxw.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxw.web.consts.AiotRedisConstants;
import com.zxw.web.po.DeviceBasicInfo;
import com.zxw.web.po.ProductBasicInfo;
import com.zxw.web.service.DeviceBasicInfoService;
import com.zxw.web.service.ProductBasicInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

//    @Autowired
    private ProductBasicInfoService productBasicInfoService;

//    @Autowired
    private DeviceBasicInfoService deviceBasicInfoService;

//    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/init")
    public void init(@RequestBody JSONObject jsonObject) {
        Boolean product = jsonObject.getBoolean("product");
        Boolean device = jsonObject.getBoolean("device");
        Boolean random = jsonObject.getBoolean("random");
        int page = 1;
        if (product) {
            while (true) {
                Page<ProductBasicInfo> productBasicInfoPage = productBasicInfoService.page(new Page<>(page, 1000));
                if (productBasicInfoPage.getRecords().isEmpty()) {
                    break;
                }
                page++;
                productBasicInfoPage.getRecords().forEach(e -> {
                    redisTemplate.opsForValue().set(AiotRedisConstants.PRODUCT + e.getProdId(), JSON.toJSONString(e));
                });
            }
        }
        if (device) {
            page = 1;
            while (true) {
                Page<DeviceBasicInfo> productBasicInfoPage = deviceBasicInfoService.page(new Page<>(page, 1000));
                if (productBasicInfoPage.getRecords().isEmpty()) {
                    break;
                }
                page++;
                productBasicInfoPage.getRecords().forEach(e -> {
                    redisTemplate.opsForValue().set(AiotRedisConstants.DEVICE + e.getDeviceCode(), JSON.toJSONString(e));
                });
            }
        }
        if (random) {
            page = 1;
            while (true) {
                Page<DeviceBasicInfo> productBasicInfoPage = deviceBasicInfoService.page(new Page<>(page, 1000));
                if (productBasicInfoPage.getRecords().isEmpty()) {
                    break;
                }
                page++;
                productBasicInfoPage.getRecords().forEach(e -> {
                    redisTemplate.opsForSet().add(AiotRedisConstants.DEVICE_RANDOM, e.getDeviceCode());
                });
            }
        }
    }

}
