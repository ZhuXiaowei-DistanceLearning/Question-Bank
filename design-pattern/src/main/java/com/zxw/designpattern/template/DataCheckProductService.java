package com.zxw.designpattern.template;

import com.zxw.vo.base.Result;

/**
 * 数据检查工具产品化对外服务接口
 * @author xxx
 * @since xxx
 * */
public interface DataCheckProductService {
    /**
     * 数据检查
     * @param requestDTO 请求参数
     * */
  <T> Result<Long> dataCheck(DataCheckRequestDTO requestDTO);
}