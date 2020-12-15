package com.zxw.designpattern.pipe;

/**
 * @author zxw
 * @date 2020-12-14 22:09
 */

public class InstanceBuildContext extends PipelineContext{
    private int year;
    private int month;
    private int date;

    @Override
    public String getName() {
        return "模型实例构建上下文";
    }
}
