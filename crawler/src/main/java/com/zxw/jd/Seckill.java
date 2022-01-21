package com.zxw.jd;

import cn.hutool.http.HttpResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author zxw
 * @date 2022/1/20 16:28
 */
@Data
@Slf4j
public class Seckill {
    private JdSession session;
    private MaoTaiSku maoTaiSku = new MaoTaiSku();
    private String num;
    private Map<String, String> initInfo;
    private Map<String, String> url;
    private Map<String, String> orderData;
    private CountDownTime countDownTime;
    private String nickName;

    public Seckill(JdSession session) {
        this.session = session;
    }

    public void yuShou() {
        String yuShouUrl = maoTaiSku.getYuShouUrl();
        HttpResponse httpResponse = session.get(yuShouUrl, null);
        log.info("{}", httpResponse);
    }
}
