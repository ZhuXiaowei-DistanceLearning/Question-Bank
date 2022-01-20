package com.zxw.jd;

import lombok.Data;
import okhttp3.*;

import java.net.HttpCookie;
import java.util.*;

/**
 * @author zxw
 * @date 2022/1/20 16:24
 */
@Data
public class SpiderSession {
    private final OkHttpClient mOkHttpClient = new OkHttpClient.Builder().cookieJar(new CookieJarManager()).build();
    private String cookieDirPath;
    private String userAgent;
    private String tokenId ;
    private List<HttpCookie> cookies;
    //头部添加session

    public void getToken(String url){
        mOkHttpClient.cookieJar().loadForRequest(HttpUrl.get(url));
    }
    /**
     * @param url       要请求的url
     * @param paramsMap post的请求参数
     * @return post的返回结果
     */
    public String post(String url, Map<String, String> paramsMap) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        Set<String> keySet = paramsMap.keySet();
        for (String key : keySet) {
            String value = paramsMap.get(key);
            formBodyBuilder.add(key, value);
        }
        FormBody formBody = formBodyBuilder.build();
        Request request = new Request
                .Builder()
                .post(formBody)
                .url(url)
                .build();
        try (Response response = mOkHttpClient.newCall(request).execute()) {
            String respStr = response.body().string();

            return respStr;
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
    }

    public String get(String url) {
        return "";
    }

    public class CookieJarManager implements CookieJar {
        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>() {
            };
        }
    }
}
