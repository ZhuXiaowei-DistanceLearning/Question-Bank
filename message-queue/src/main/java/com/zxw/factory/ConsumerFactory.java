package com.zxw.factory;

import com.zxw.base.ConsumerHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zxw
 * @date 2021/6/21 17:39
 */
@Component
public class ConsumerFactory implements ApplicationContextAware, InitializingBean {
    private ApplicationContext applicationContext;
    private Map<String, ConsumerHandler> DATA_MAP = new HashMap<>();

    public ConsumerHandler getHandler(String txCode) {
        return DATA_MAP.get(txCode);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansOfType(ConsumerHandler.class)
                .values()
                .forEach(handler -> {
                    if (StringUtils.isNotEmpty(handler.getHandlerName())) {
                        DATA_MAP.put(handler.getHandlerName(), handler);
                    }
                });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
