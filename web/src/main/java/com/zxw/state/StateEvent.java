package com.zxw.state;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @author zxw
 * @date 2022/1/12 10:22
 */
@WithStateMachine
@Slf4j
public class StateEvent {
    @OnTransition(target = "UNPAID")
    public void create() {
        log.info("创建");
    }
}
