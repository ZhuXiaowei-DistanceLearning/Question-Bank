package com.zxw.jd;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author zxw
 * @date 2022/1/21 10:23
 */
public class PropertiesUtils {
    static Properties properties;

    static {
        try {
            properties = PropertiesLoaderUtils.loadAllProperties("config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String getUserAgent() {
        return properties.getProperty("DEFAULT_USER_AGENT");
    }
}
