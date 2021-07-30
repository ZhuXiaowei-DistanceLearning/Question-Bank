package com.zxw.designpattern.template.strategy;

import com.zxw.designpattern.template.CompareBO;
import com.zxw.designpattern.template.DataCheckRequestDTO;
import com.zxw.designpattern.template.strategy.AbstractCompareModeStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查落地配覆盖范围 统计信息 策略类
 * @author xxx
 * @since xxx
 * */
public class LandingCoverAreaStatisticsStrategy extends AbstractCompareModeStrategy {
    @Override
    public <T> List<T> doHandle(List<CompareBO> list, DataCheckRequestDTO requestDTO){
        List<T> resultList = new ArrayList<>();
    //检查结果处理逻辑
        return resultList;
    }
}