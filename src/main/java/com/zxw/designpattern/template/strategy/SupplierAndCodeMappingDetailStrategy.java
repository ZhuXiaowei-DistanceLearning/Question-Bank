package com.zxw.designpattern.template.strategy;

import com.zxw.designpattern.template.CompareBO;
import com.zxw.designpattern.template.DataCheckRequestDTO;
import com.zxw.designpattern.template.strategy.AbstractCompareModeStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查配送资源和编码映射一致 详细信息 策略类
 * @author xxx
 * @since xxx
 * */
public class SupplierAndCodeMappingDetailStrategy extends AbstractCompareModeStrategy {
    @Override
    public <T> List<T> doHandle(List<CompareBO> list, DataCheckRequestDTO requestDTO){
        List<T> resultList = new ArrayList<>();
    //检查结果处理逻辑
        return resultList;
    }
}