package com.zxw.designpattern.pipe;

/**
 * @author zxw
 * @date 2020-12-14 22:06
 */

public class PipelineContext {
    private long startTime;
    private long endTime;

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
