package com.zxw.designpattern.template;

import java.util.List;

/**
 * 检查资源映射和编码映射一致
 * @author xxx
 * @since xxx
 * */
public class CheckSupplierAndCodeMappingService extends AbstractDataCheckProductService{
    @Override
    public <T> void runDataCheck(List<T> resultList, DataCheckRequestDTO requestDTO){
        //自己的检查逻辑
    }
}