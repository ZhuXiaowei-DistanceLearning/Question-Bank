package com.zxw.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxw.web.mapper.ProductBasicInfoMapper;
import com.zxw.web.po.ProductBasicInfo;
import com.zxw.web.service.ProductBasicInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxw
 * @since 2022-04-28
 */
@Service
@Slf4j
public class ProductBasicInfoServiceImpl extends ServiceImpl<ProductBasicInfoMapper, ProductBasicInfo> implements ProductBasicInfoService {

}
