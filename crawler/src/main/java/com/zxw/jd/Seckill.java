package com.zxw.jd;

import lombok.Data;

import java.util.Map;

/**
 * @author zxw
 * @date 2022/1/20 16:28
 */
@Data
public class Seckill {
    private SpiderSession spiderSession;
    private QrLogin qrLogin;
    private String skuId;
    private String num;
    private Map<String,String> initInfo;
    private Map<String,String> url;
    private Map<String,String> orderData;
    private CountDownTime countDownTime;
    private String nickName;
}
