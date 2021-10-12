package com.zxw.web;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author zxw
 * @date 2021/10/11 9:38
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private ReactiveStringRedisTemplate reactiveStringRedisTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 安全验收标准:
     * 短信验证码有效期2分钟
     * 验证码为6位纯数字
     * 每个手机号60秒内只能发送一次短信验证码，且这一规则的校验必须在服务器端执行
     * 同一个手机号在同一时间内可以有多个有效的短信验证码
     * 保存于服务器端的验证码，至多可被使用3次（无论和请求中的验证码是否匹配），随后立即作废，以防止暴力攻击
     * 短信验证码不可直接记录到日志文件
     * 发送短信验证码之前，先验证图形验证码是否正确（可选）
     * 集成第三方API做登录保护（可选）
     */
    @GetMapping("/getSms/{phone}")
    public void getSms(@PathVariable("phone") String phone) {
        String verificationCode = redisTemplate.opsForValue().get(phone);
        log.info("手机号:{}是否获取过验证码:{}", phone, !Objects.isNull(verificationCode));
        if (StringUtils.isEmpty(verificationCode)) {
            verificationCode = RandomUtil.randomNumbers(6);
        }
    }
}
