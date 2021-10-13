package com.zxw.web;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zxw.consts.Constants;
import com.zxw.exception.BusinessException;
import com.zxw.utils.PhoneUtils;
import com.zxw.vo.LoginReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
    public Mono<Void> getSms(@PathVariable("phone") String phone) {
        String phoneNum = PhoneUtils.getPhoneNum();
        String phoneKey = "login_" + phone;
        String verificationObject = redisTemplate.opsForValue().get(phoneKey);
        log.info("手机号:{}是否获取过验证码:{}", phoneNum, !Objects.isNull(verificationObject));
        JSONObject codeInfo = JSON.parseObject(verificationObject);
        if (codeInfo == null) {
            codeInfo = new JSONObject();
            codeInfo.put("number", 1);
        } else {
            Long applicationTime = codeInfo.getLong("application_time");
            if (System.currentTimeMillis() - applicationTime < 3600 * 60) {
                throw new BusinessException("", "60秒内仅可申请一次验证码");
            }
        }
        String verificationCode = RandomUtil.randomNumbers(6);
        codeInfo.put("verificationCode", verificationCode);
        codeInfo.put("application_time", System.currentTimeMillis());
        redisTemplate.opsForValue().set(phoneKey, codeInfo.toJSONString());
        return Mono.empty();
    }

    @PostMapping("/login")
    public Mono<Void> login(@RequestBody LoginReqVo reqVo) {
        String phoneKey = "login_" + reqVo.getPhone();
        String phoneCodeInfo = redisTemplate.opsForValue().get(phoneKey);
        if (StringUtils.isEmpty(phoneCodeInfo)) {
            throw new BusinessException("", "60秒内仅可申请一次验证码");
        }
        JSONObject codeInfo = JSON.parseObject(phoneCodeInfo);
        Long applicationTime = codeInfo.getLong("application_time");
        Integer number = codeInfo.getInteger("number");
        if (number > 3) {
            throw new BusinessException("", "已超过最大使用次数,请重新申请");
        }
        if (System.currentTimeMillis() - applicationTime > 2 * Constants.MINUTE) {
            throw new BusinessException("", "该验证码已失效,请重新申请");
        }
        return Mono.empty();
    }
}
