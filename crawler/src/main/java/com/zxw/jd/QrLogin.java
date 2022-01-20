package com.zxw.jd;

import cn.hutool.http.HttpUtil;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zxw
 * @date 2022/1/20 16:26
 */
@Data
public class QrLogin {
    private String qrCodeImgFile;
    private SpiderSession spiderSession;
    private String isLogin;
    private String loginUrl = "https://qr.m.jd.com/check";

    public boolean login() {
        Map<String, Object> payload = new HashMap<>();
        spiderSession.getToken(loginUrl);
        payload.put("appid", "133");
        payload.put("callback", "jQuery1165357");
        payload.put("token", "'ey5s6xl8qjc05n2rxcgb6qtzjxpxy6xc'");
        payload.put("_", String.valueOf(System.currentTimeMillis()));
        HttpUtil.get(loginUrl, payload);
//        spiderSession.get(loginUrl, payload);
        return false;
    }

    public void getLoginQrCode() {
        String url = "https://qr.m.jd.com/show";
        Map<String, Object> payload = new HashMap<>();
        spiderSession.getToken(loginUrl);
        payload.put("appid", "133");
        payload.put("size", "147");
        payload.put("t", String.valueOf(System.currentTimeMillis()));
        String s = HttpUtil.get(url, payload);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(s.getBytes());
        System.out.println();
    }
}
