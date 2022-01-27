package com.zxw.config;

import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zxw
 * @date 2022-01-27 14:56
 */
@Slf4j
public class GiteeFeign {
    interface Gitee {
        @RequestLine("GET /api/v5/repos/{owner}/{repo}/stargazers?access_token=80998f907bbd005a61e1be10e4a51cb5&page=1&per_page=20")
        List<Stargazers> repo(@Param("owner") String owner, @Param("repo") String repo);

        default List<Stargazers> stargazers(String owner, String repo) {
            return repo(owner, repo);
        }

        static Gitee connect() {
            final Decoder decoder = new GsonDecoder();
            final Encoder encoder = new GsonEncoder();
            return Feign.builder()
                    .encoder(encoder)
                    .decoder(decoder)
                    .logger(new Logger.ErrorLogger())
                    .logLevel(Logger.Level.BASIC)
                    .requestInterceptor(template -> {
//                        template.header(
//                                // not available when building PRs...
//                                // https://docs.travis-ci.com/user/environment-variables/#defining-encrypted-variables-in-travisyml
//                                "Authorization",
//                                "token 383f1c1b474d8f05a21e7964976ab0d403fee071");
                    })
                    .options(new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true))
                    .target(Gitee.class, "https://gitee.com");
        }
    }

    @Data
    class Stargazers {
        private String id;
        private String login;
        private String name;
        private String avatar_url;
        private String url;
        private String html_url;
        private String remark;
        private String followers_url;
        private String following_url;
        private String gists_url;
        private String starred_url;
        private String subscriptions_url;
        private String organizations_url;
        private String repos_url;
        private String events_url;
        private String received_events_url;
        private String type;
        private String star_at;
    }


    public static void main(String[] args) {
        Gitee connect = Gitee.connect();
        List<Stargazers> star = connect.repo("xiaowei_zxw", "JSDX-JwSystem");
        star.forEach(e -> {
            log.info("获取到gitee项目star情况:{}", e);
        });
    }


}
