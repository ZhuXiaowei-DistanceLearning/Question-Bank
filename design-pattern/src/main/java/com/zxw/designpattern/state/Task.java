package com.zxw.designpattern.state;

import lombok.Data;

@Data
class Task {
    private Long taskId;
    // 初始化为初始态
    private State state = new TaskInit();

    // 更新状态
    public void updateState(ActionType actionType) {
        state.update(this, actionType);
    }
}