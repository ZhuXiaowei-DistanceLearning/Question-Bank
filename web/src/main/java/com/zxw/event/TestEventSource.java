package com.zxw.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author zxw
 * @date 2022/4/12 15:16
 */
public class TestEventSource extends ApplicationEvent {
    public TestEventSource(Object source) {
        super(source);
    }
}
