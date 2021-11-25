package com.zxw.designpattern.template;

import java.util.List;

/**
 * 检查区域内落地配必须三级全覆盖
 * @author xxx
 * @since xxx
 * */
public class CheckLandingCoverAreaService extends AbstractDataCheckProductService{
    @Override
    public <T> void runDataCheck(List<T> resultList, DataCheckRequestDTO requestDTO){
        //自己的检查逻辑
    }
}