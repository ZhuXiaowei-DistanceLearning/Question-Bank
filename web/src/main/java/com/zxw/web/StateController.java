package com.zxw.web;

import com.zxw.state.Events;
import com.zxw.state.States;
import com.zxw.vo.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxw
 * @date 2022/1/12 10:24
 */
@RestController
@RequestMapping("/mq")
public class StateController {
    @Autowired
    private StateMachine<States, Events> stateMachine;

    @GetMapping
    public Result test1() {
        stateMachine.start();
        stateMachine.sendEvent(Events.PAY);
        return Result.success();
    }
}
