package com.zxw.jd;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.HttpCookie;
import java.util.List;

/**
 * @author zxw
 * @date 2022/1/20 16:26
 */
@Data
@Slf4j
public class QrLogin {
    private String qrCodeImgFile;
    private SpiderSession spiderSession;
    private String isLogin;

    public boolean login() {
        this.getLoginQrCode();

        while (true) {
            String loginUrl = "https://qr.m.jd.com/check?callback=jQuery" + RandomStringUtils.randomNumeric(7) + "&appid=133&token=" + spiderSession.getTokenId() + "&_=" + System.currentTimeMillis();
            HttpRequest get = HttpUtil.createGet(loginUrl);
            log.info("检查jd是否登录地址:{}", loginUrl);
            get.cookie(spiderSession.getCookies());
            HttpResponse response = get.execute();
            System.out.println(response.body());
        }
    }

    public void getLoginQrCode() {
        File destFile = new File("crawler/src/main/resources/qr.png");
        String url = "https://qr.m.jd.com/show?appid=133&size=147&t=" + System.currentTimeMillis();
        HttpResponse response = HttpUtil.createGet(url, true).execute();
        List<HttpCookie> cookies = response.getCookies();
        cookies.forEach(e -> {
            if (StringUtils.equals(e.getName(), "wlfstk_smdl")) {
                spiderSession.setTokenId(e.getValue());
                return;
            }
        });
        spiderSession.setCookies(cookies);
        response.writeBody(destFile, null);
//        HttpUtil.downloadFile(url, );
    }
}
