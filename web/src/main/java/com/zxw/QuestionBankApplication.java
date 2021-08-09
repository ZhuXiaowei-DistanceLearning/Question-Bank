package com.zxw;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

/**
 * @author zxw
 * @date 2020/6/28 10:19
 */
public class QuestionBankApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WebConfigure.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }
}
