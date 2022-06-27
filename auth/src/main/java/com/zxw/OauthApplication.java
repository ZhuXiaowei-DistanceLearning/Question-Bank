package com.zxw;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zxw
 * @date 2022/6/27 21:34
 */
@SpringBootApplication
//@MapperScan(basePackages = "com.zxw.web.mapper ")
@Slf4j
public class OauthApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OauthApplication.class);
//        app.setWebApplicationType(WebApplicationType.REACTIVE);
//        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }
}
