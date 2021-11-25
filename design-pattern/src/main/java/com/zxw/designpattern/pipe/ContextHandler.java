package com.zxw.designpattern.pipe;

/**
 * @author zxw
 * @date 2020-12-14 22:08
 */
public interface ContextHandler<T extends PipelineContext> {
    boolean handler(T context);
}
