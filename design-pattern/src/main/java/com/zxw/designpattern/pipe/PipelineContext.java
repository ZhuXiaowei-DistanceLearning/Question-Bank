package com.zxw.designpattern.pipe;

import lombok.Data;

/**
 * @author zxw
 * @date 2020-12-14 22:06
 */
@Data
public class PipelineContext {
    private long startTime;
    private long endTime;

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
