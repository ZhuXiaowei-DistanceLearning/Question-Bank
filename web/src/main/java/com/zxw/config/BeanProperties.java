package com.zxw.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

/**
 * @author zxw
 * @date 2022/4/12 10:57
 */
@Data
@Component
@ConfigurationProperties(prefix = "bean", ignoreInvalidFields = true)
@Validated
public class BeanProperties {
    private String key;
    private Map<String, String> map;
}
