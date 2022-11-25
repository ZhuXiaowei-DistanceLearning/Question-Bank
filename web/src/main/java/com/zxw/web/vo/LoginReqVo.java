package com.zxw.web.vo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


/**
 * @author zxw
 * @date 2021/10/13 9:31
 */
@Data
public class LoginReqVo {
    @NotEmpty(message = "手机号不能为空")
    private String phone;
    @NotEmpty(message = "验证码不能为空")
    private String verificationCode;
}
