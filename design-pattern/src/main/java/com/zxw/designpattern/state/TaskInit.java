package com.zxw.designpattern.state;

import com.zxw.designpattern.state.subject.ActivityObserver;
import com.zxw.designpattern.state.subject.TaskManageObserver;

// 任务初始状态
class TaskInit implements State {
    @Override
    public void update(Task task, ActionType actionType) {
        if  (actionType == ActionType.START) {
            TaskOngoing taskOngoing = new TaskOngoing();
            taskOngoing.add(new ActivityObserver());
            taskOngoing.add(new TaskManageObserver());
            task.setState(taskOngoing);
        }
    }
}