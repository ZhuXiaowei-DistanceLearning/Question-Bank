package com.zxw;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

/**
 * @author zxw
 * @date 2020/6/28 10:19
 */
@SpringBootApplication
public class QuestionBankApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(QuestionBankApplication.class);
        app.setWebApplicationType(WebApplicationType.REACTIVE);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }
}
