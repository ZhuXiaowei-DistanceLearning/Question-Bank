package com.zxw.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zxw
 * @date 2022/4/12 15:40
 */
@Configuration
@Slf4j
public class BeanFilterRegistration {
    @Bean
    public FilterRegistrationBean authFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new BeanFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(2);
        return registration;
    }

}
