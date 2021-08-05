package designpattern.status.processor;

import designpattern.status.processor.annotaion.StateProcessor;

/**
 * 插件处理器
 */
public interface PluginHandler<T, C> extends StateProcessor<T, C> {
}