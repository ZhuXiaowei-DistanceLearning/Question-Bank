package com.zxw.designpattern.pipe;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zxw
 * @date 2020-12-14 22:12
 */
@Component
public class PipelineRouteConfig implements ApplicationContextAware {
    ApplicationContext applicationContext;
    private static final Map<Class<? extends PipelineContext>, List<Class<? extends ContextHandler<? extends PipelineContext>>>> PIPELINE_ROUTE_MAP = new HashMap<>();

    static {
        PIPELINE_ROUTE_MAP.put(InstanceBuildContext.class, Arrays.asList(ModelInstanceCreator.class, InputDataPreChecker.class));
    }

    @Bean("pipelineRouteMap")
    public Map<Class<? extends PipelineContext>, List<? extends ContextHandler<? extends PipelineContext>>> getHandlerPipelineMap() {
        return PIPELINE_ROUTE_MAP.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, this::toPipeline));
    }

    public List<? extends ContextHandler<? extends PipelineContext>> toPipeline(Map.Entry<Class<? extends PipelineContext>, List<Class<? extends ContextHandler<? extends PipelineContext>>>> entry) {
        return entry.getValue()
                .stream()
                .map(applicationContext::getBean)
                .collect(Collectors.toList());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
