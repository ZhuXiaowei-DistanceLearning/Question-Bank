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

    public String oath() {
        String url = "http://jenkins.local.ap-ec.cn/securityRealm/finishLogin?state=%2FgitlabLogout%2F";
        String accessToken = getToken();
        url = url + "&code=" + accessToken;
        HttpResponse response = HttpUtil.createGet(url)
                .execute();
        HttpResponse location = HttpUtil.createGet(response.header("Location"))
                .execute();
        return location.header("Set-Cookie");
    }

    public String getToken() {
        Map<String, Object> form = new HashMap<>();
        form.put("grant_type", "password");
        form.put("username", "zhuxw@ap-ec.cn");
        form.put("password", "a520201314");
        String url = "https://git.ap-ec.cn/oauth/token";
        HttpResponse response = HttpUtil.createPost(url)
                .form(form)
                .execute();
        JSONObject res = JSON.parseObject(response.body());
        return res.getString("access_token");
    }

    public static void main(String[] args) {
        GitlabOauth gitlab = new GitlabOauth();
        gitlab.oath();
    }
}
