package com.zxw.designpattern.pipe;

import lombok.Data;

/**
 * @author zxw
 * @date 2020-12-14 22:09
 */
@Data
public class InstanceBuildContext extends PipelineContext{
    private int year;
    private int month;
    private int date;

    @Override
    public String getName() {
        return "模型实例构建上下文";
    }
}
