package com.zxw.web.service.impl;

import com.zxw.web.service.BeanService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * @author zxw
 * @date 2022/4/12 10:36
 */
//@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@Primary
//@Priority(value = 1)
//@Order(value = 1)
public class BeanServiceImpl implements BeanService{
}
