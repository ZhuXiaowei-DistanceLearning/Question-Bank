package com.zxw.jd;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.HttpCookie;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zxw
 * @date 2022/1/21 9:06
 */
@Data
@Slf4j
public class JdQrLogin implements QrLoginService {
    private String qrCodeUrl = "https://qr.m.jd.com/show?appid=133&size=147&t=" + System.currentTimeMillis();
    private JdSession session;

    public JdQrLogin(JdSession jdSession) {
        this.session = jdSession;
    }

    @Override
    public void login() {
        this.getQrCodeImg();
        Map<String, String> headers = session.getHeaders();
        List<HttpCookie> cookies = session.getCookies();
        headers.put("Referer", "https://passport.jd.com/new/login.aspx");
        while (true) {
            String loginUrl = "https://qr.m.jd.com/check?callback=jQuery" + RandomStringUtils.randomNumeric(7) + "&appid=133&token=" + session.getSessionId() + "&_=" + System.currentTimeMillis();
            HttpRequest get = HttpUtil.createGet(loginUrl);
            get.cookie(cookies);
            get.addHeaders(headers);
            HttpResponse response = get.execute();
            String result = response.body();
            JSONObject jsonObject = JdParseUtils.parseResult(result);
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            log.info("登录结果:{}-{}", code, msg);
            if (Objects.equals(code, "200")) {
                session.setTicket(jsonObject.getString("ticket"));
                if (this.checkTicket() && this.validateCookie()) {
                    log.info("登录成功");
                    break;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkTicket() {
        Map<String, String> headers = session.getHeaders();
        headers.put("Referer", "https://passport.jd.com/uc/login?ltype=logout");
        String url = "https://passport.jd.com/uc/qrCodeTicketValidation?t=" + session.getTicket();
        HttpRequest get = HttpUtil.createGet(url);
        get.addHeaders(headers);
        HttpResponse response = get.execute();
        session.getCookies().addAll(response.getCookies());
        JSONObject result = JdParseUtils.parseResult(response.body());
        return Objects.equals(result.getInteger("returnCode"), 0) ? true : false;
    }

    private boolean validateCookie() {
        String url = "https://order.jd.com/center/list.action?rid=" + System.currentTimeMillis();
        HttpRequest get = HttpUtil.createGet(url);
        get.addHeaders(session.getHeaders());
        HttpResponse response = get.execute();
        JSONObject result = JdParseUtils.parseResult(response.body());
        return true;
    }

    private void getQrCodeImg() {
        File destFile = new File("crawler/src/main/resources/qr.png");
        HttpResponse response = HttpUtil.createGet(qrCodeUrl, true).execute();
        List<HttpCookie> cookies = response.getCookies();
        cookies.forEach(e -> {
            if (StringUtils.equals(e.getName(), "wlfstk_smdl")) {
                session.setSessionId(e.getValue());
                return;
            }
        });
        session.setCookies(cookies);
        response.writeBody(destFile, null);
    }

}
