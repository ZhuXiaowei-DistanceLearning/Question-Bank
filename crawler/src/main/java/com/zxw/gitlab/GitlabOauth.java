package com.zxw.gitlab;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zxw
 * @date 2022/3/12 19:09
 */
@Slf4j
public class GitlabOauth {

    public Map<String, String> oauth() {
        String url = "http://jenkins.local.ap-ec.cn/securityRealm/finishLogin?state=%2FgitlabLogout%2F";
        String accessToken = getToken();
        url = url + "&code=" + accessToken;
        HttpResponse response = HttpUtil.createGet(url)
                .execute();
        HttpResponse location = HttpUtil.createGet(response.header("Location"))
                .execute();
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "jenkins-timestamper-offset=-28800000; jenkins-timestamper=system; jenkins-timestamper-local=true; screenResolution=1920x1080; " + location.header("Set-Cookie"));
        headers.put("X-Instance-Identity", location.header("X-Instance-Identity"));
        headers.put("X-Jenkins-Session", location.header("X-Instance-Identity"));
        headers.put("Host", "jenkins.local.ap-ec.cn");
        headers.put("Connection", "Keep-Alive");
        headers.put("User-Agent", "Apache-HttpClient/4.5.13 (Java/1.8.0_144)");
        headers.put("Accept-Encoding", "gzip,deflate");
        return headers;
    }

    public String getToken() {
        Map<String, Object> form = new HashMap<>();
        form.put("grant_type", "password");
        form.put("username", "develop");
        form.put("password", "Admin!@123");
        String url = "https://git.ap-ec.cn/oauth/token";
        HttpResponse response = HttpUtil.createPost(url)
                .form(form)
                .execute();
        JSONObject res = JSON.parseObject(response.body());
        return res.getString("access_token");
    }

    public static void main(String[] args) {
        GitlabOauth gitlab = new GitlabOauth();
        gitlab.oauth();
    }
}
