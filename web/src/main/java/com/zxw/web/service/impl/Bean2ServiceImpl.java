package com.zxw.web.service.impl;

import com.zxw.web.service.BeanService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author zxw
 * @date 2022/4/12 10:36
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Bean2ServiceImpl implements BeanService {
}
