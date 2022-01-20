package com.zxw.jd;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zxw
 * @date 2022/1/20 16:23
 */
@Slf4j
public class BuyMain {
    public static void main(String[] args) {
        QrLogin qrLogin = new QrLogin();
        SpiderSession spiderSession = new SpiderSession();
        qrLogin.setSpiderSession(spiderSession);
        qrLogin.login();
    }
}
