package com.zxw.web;

import com.zxw.config.BeanProperties;
import com.zxw.web.service.BeanService;
import com.zxw.vo.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 当一个单例bean使用autowired注解标记时，这个属性值会固定下来
 * 1.每次通过applicationContext重新获取bean
 * 2.使用@Lookup
 * 3.配置ScopedProxyMode.TARGET_CLASS
 * 4.使用的bean也配置@Scope注解为多例
 * @author zxw
 * @date 2022/4/12 10:35
 */
@RequestMapping("/bean")
@RestController
@Slf4j
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BeanController {
    @Autowired
    @Qualifier("beanServiceImpl")
    private BeanService beanService;

    @Value("#{beanProperties.key}")
    private String value;

    @Autowired
    private BeanProperties beanProperties;

    @GetMapping
    public Result get() {
        log.info("{}", beanProperties);
        log.info("{}", value);
        log.info("{}", beanService);
        return Result.success();
    }

    @GetMapping("/aop")
    public Result aop() {
        log.info("{}", AopContext.currentProxy());
        return Result.success();
    }
}
