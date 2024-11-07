package com.zxw.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

/**
 * @author zxw
 * @date 2024-10-18 16:09
 */
@Slf4j
public class JueJinUtils {

    static String cookie = "__tea_cookie_tokens_2608=%257B%2522web_id%2522%253A%25227296676916919485961%2522%252C%2522user_unique_id%2522%253A%25227296676916919485961%2522%252C%2522timestamp%2522%253A1698889995409%257D; n_mh=vkUQfuxPEI6cnJ9r6-NHZJyovzkEa36ZcaF4jW-VvFc; is_staff_user=false; store-region=cn-gd; store-region-src=uid; _ga=GA1.2.943205446.1724917962; _ga_S695FMNGPJ=GS1.2.1724917962.1.0.1724917962.60.0.0; _tea_utm_cache_2018={%22utm_source%22:%22community%22%2C%22utm_medium%22:%22marscode%22}; _tea_utm_cache_2608={%22utm_source%22:%22community%22%2C%22utm_medium%22:%22marscode%22}; csrf_session_id=2451a4ed3b45a9d3ed41a580af52976d; passport_csrf_token=2c7ac38d1834e2121bacc924368f1d62; passport_csrf_token_default=2c7ac38d1834e2121bacc924368f1d62; passport_auth_status=7ce2e33199cd80033af6bba7103f49ee%2C; passport_auth_status_ss=7ce2e33199cd80033af6bba7103f49ee%2C; sid_guard=c3548c1fd5aa742ccaabe5e18dffb71b%7C1729238682%7C31536000%7CSat%2C+18-Oct-2025+08%3A04%3A42+GMT; uid_tt=1f8da6f017bcd6ba3a74f719d13479ab; uid_tt_ss=1f8da6f017bcd6ba3a74f719d13479ab; sid_tt=c3548c1fd5aa742ccaabe5e18dffb71b; sessionid=c3548c1fd5aa742ccaabe5e18dffb71b; sessionid_ss=c3548c1fd5aa742ccaabe5e18dffb71b; sid_ucp_v1=1.0.0-KGUzZTM5ZGQwNGQzODAyOWRhNTM0Nzg4MjRmYTllOWUyYWYxZDY4MTEKFwiX0oD1moylAhCarci4BhiwFDgCQPEHGgJsZiIgYzM1NDhjMWZkNWFhNzQyY2NhYWJlNWUxOGRmZmI3MWI; ssid_ucp_v1=1.0.0-KGUzZTM5ZGQwNGQzODAyOWRhNTM0Nzg4MjRmYTllOWUyYWYxZDY4MTEKFwiX0oD1moylAhCarci4BhiwFDgCQPEHGgJsZiIgYzM1NDhjMWZkNWFhNzQyY2NhYWJlNWUxOGRmZmI3MWI";

    static String cookie1 = "_tea_utm_cache_2608=undefined; __tea_cookie_tokens_2608=%257B%2522web_id%2522%253A%25227296676916919485961%2522%252C%2522user_unique_id%2522%253A%25227296676916919485961%2522%252C%2522timestamp%2522%253A1698889995409%257D; is_staff_user=false; store-region=cn-gd; store-region-src=uid; _ga=GA1.2.943205446.1724917962; _ga_S695FMNGPJ=GS1.2.1724917962.1.0.1724917962.60.0.0; _tea_utm_cache_2018={%22utm_source%22:%22community%22%2C%22utm_medium%22:%22marscode%22}; csrf_session_id=2451a4ed3b45a9d3ed41a580af52976d; passport_csrf_token=a8f7847a5e9f956f3f3e2b22346382a1; passport_csrf_token_default=a8f7847a5e9f956f3f3e2b22346382a1; n_mh=9-mIeuD4wZnlYrrOvfzG3MuT6aQmCUtmr8FxV8Kl8xY; sid_guard=33a1d6b5d8c499f32134e55ef7f40602%7C1729237667%7C31536000%7CSat%2C+18-Oct-2025+07%3A47%3A47+GMT; uid_tt=284e302ee678f13e4c596e6e37cc6c70; uid_tt_ss=284e302ee678f13e4c596e6e37cc6c70; sid_tt=33a1d6b5d8c499f32134e55ef7f40602; sessionid=33a1d6b5d8c499f32134e55ef7f40602; sessionid_ss=33a1d6b5d8c499f32134e55ef7f40602; sid_ucp_v1=1.0.0-KDhiOTg4ZjQxNzQ4NWFhZjNkNTVkNjc4YTRkNjJiZmJkNmZlYzYwMmUKFgjI75DA_fWlBhCjpci4BhiwFDgIQAsaAmxmIiAzM2ExZDZiNWQ4YzQ5OWYzMjEzNGU1NWVmN2Y0MDYwMg; ssid_ucp_v1=1.0.0-KDhiOTg4ZjQxNzQ4NWFhZjNkNTVkNjc4YTRkNjJiZmJkNmZlYzYwMmUKFgjI75DA_fWlBhCjpci4BhiwFDgIQAsaAmxmIiAzM2ExZDZiNWQ4YzQ5OWYzMjEzNGU1NWVmN2Y0MDYwMg";

    public static void main(String[] args) {

    }

    public static void draw() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.juejin.cn/growth_api/v1/lottery/draw?aid=2608&uuid=7296676916919485961&spider=0&msToken=CrBjaDpJZmV8vZJ_1tQ4QT_v-SfH-ZW-EBWiqKzTR9kl5zUQUH_QwR7396rNlDoZbhS3YvmAwibKGLyTtsypXHslhYJJswH6UOaXBHoF4TEdkaslXyAqMlDl15AJEQ%3D%3D&a_bogus=QyMEDOg2Msm1I730nwkz99RE6my0YWRRgZENXM%2FR-ULW")
                .addHeader("Cookie", cookie)
                .addHeader("Origin", "https://juejin.cn")
                .addHeader("priority", "u=1, i")
                .addHeader("referer", "https://juejin.cn/")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36 Edg/129.0.0.0")
                .addHeader("user-x-secsdk-csrf-token", "000100000001a69936b508d0cfb4011af07360f4772eabc14bd17ec5edc9203c72524d62f5d617ff7ce37fb0afb4")
                .post(RequestBody.create(MediaType.parse("application/json"), "{}"))
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                log.info("res:{}", res);
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    public static void check() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.juejin.cn/growth_api/v1/check_in?aid=2608&uuid=7296676916919485961&spider=0&msToken=gKfW3hOLO7KPgY2RI81ul8PiCSXXsb3N9r7eQwii5GsUK4B-ZC0yVLpHQuLJIupLkDZn1EhFtCXUdKviBX8Th_7V8gQb-fT0Vmok6k0Gf9roasTZkpF7Cq89Izkkvg%3D%3D&a_bogus=dfsx6Og2Msm1pXvPehkz97DEEXS0YW47gZEPX8XQDzLk")
                .addHeader("Cookie", cookie)
                .addHeader("Origin", "https://juejin.cn")
                .addHeader("priority", "u=1, i")
                .addHeader("referer", "https://juejin.cn/")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36 Edg/129.0.0.0")
                .addHeader("user-x-secsdk-csrf-token", "000100000001a69936b508d0cfb4011af07360f4772eabc14bd17ec5edc9203c72524d62f5d617ff7ce37fb0afb4")
                .post(RequestBody.create(MediaType.parse("application/json"), "{}"))
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                log.info("res:{}", res);
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    public static void draw1() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.juejin.cn/growth_api/v1/check_in?https://api.juejin.cn/growth_api/v1/lottery/draw?aid=2608&uuid=7296676916919485961&spider=0&msToken=CrBjaDpJZmV8vZJ_1tQ4QT_v-SfH-ZW-EBWiqKzTR9kl5zUQUH_QwR7396rNlDoZbhS3YvmAwibKGLyTtsypXHslhYJJswH6UOaXBHoF4TEdkaslXyAqMlDl15AJEQ%3D%3D&a_bogus=QyMEDOg2Msm1I730nwkz99RE6my0YWRRgZENXM%2FR-ULW")
                .addHeader("Cookie", cookie1)
                .addHeader("Origin", "https://juejin.cn")
                .addHeader("priority", "u=1, i")
                .addHeader("referer", "https://juejin.cn/")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36 Edg/129.0.0.0")
                .addHeader("user-x-secsdk-csrf-token", "000100000001a69936b508d0cfb4011af07360f4772eabc14bd17ec5edc9203c72524d62f5d617ff7ce37fb0afb4")
                .post(RequestBody.create(MediaType.parse("application/json"), "{}"))
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                log.info("res:{}", res);
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    public static void check1() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.juejin.cn/growth_api/v1/check_in?aid=2608&uuid=7296676916919485961&spider=0&msToken=gKfW3hOLO7KPgY2RI81ul8PiCSXXsb3N9r7eQwii5GsUK4B-ZC0yVLpHQuLJIupLkDZn1EhFtCXUdKviBX8Th_7V8gQb-fT0Vmok6k0Gf9roasTZkpF7Cq89Izkkvg%3D%3D&a_bogus=dfsx6Og2Msm1pXvPehkz97DEEXS0YW47gZEPX8XQDzLk")
                .addHeader("Cookie", cookie1)
                .addHeader("Origin", "https://juejin.cn")
                .addHeader("priority", "u=1, i")
                .addHeader("referer", "https://juejin.cn/")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36 Edg/129.0.0.0")
                .addHeader("user-x-secsdk-csrf-token", "000100000001a69936b508d0cfb4011af07360f4772eabc14bd17ec5edc9203c72524d62f5d617ff7ce37fb0afb4")
                .post(RequestBody.create(MediaType.parse("application/json"), "{}"))
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                log.info("res:{}", res);
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
