package com.zxw;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.core.env.StandardEnvironment;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * @author zxw
 * @date 2020/6/28 10:19
 */
@SpringBootApplication
@Slf4j
public class QuestionBankApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(QuestionBankApplication.class);
        app.setWebApplicationType(WebApplicationType.REACTIVE);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }

    @Autowired
    private StandardEnvironment env;

    @PostConstruct
    public void init() {
        Arrays.asList("spring.profiles.active").forEach(key -> {
            env.getPropertySources().forEach(propertySource -> {
                if (propertySource.containsProperty(key)) {
                    log.info("{} -> {} 实际取值：{}", propertySource, propertySource.getProperty(key), env.getProperty(key));
                }
            });
        });
        log.info("配置优先级：");
        env.getPropertySources().stream().forEach(System.out::println);
    }
}
