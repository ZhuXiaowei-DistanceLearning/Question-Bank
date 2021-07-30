package com.zxw.designpattern.template.strategy;

import com.zxw.designpattern.template.CompareBO;
import com.zxw.designpattern.template.DataCheckRequestDTO;

import java.util.List;

/**
 * 检查结果策略对外接口
 * @author xxx
 * @since xxx
 * */
public interface CompareModeStrategy {
    /**
     * 具体操作
     *
     * @param list
     * @param requestDTO
     * @return 结果集
     * */
    <T> List<T> doHandle(List<CompareBO> list, DataCheckRequestDTO requestDTO);
}