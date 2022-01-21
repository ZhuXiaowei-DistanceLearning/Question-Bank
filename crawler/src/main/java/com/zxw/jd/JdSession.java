package com.zxw.jd;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.Data;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zxw
 * @date 2022/1/21 9:08
 */
@Data
public class JdSession {
    private String sessionId;
    private String ticket;
    private Map<String, String> headers;
    private List<HttpCookie> cookies;

    public JdSession() {
        this.headers = new HashMap<>();
        headers.put("User-Agent", PropertiesUtils.getUserAgent());
    }

    public HttpResponse get(String url, Map<String, String> headerMap) {
        if (headerMap != null) {
            headers.putAll(headerMap);
        }
        HttpRequest get = HttpUtil.createGet(url);
        get.addHeaders(headers);
        get.cookie(cookies);
        return get.execute();
    }
}
