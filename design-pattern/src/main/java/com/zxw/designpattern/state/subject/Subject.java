package com.zxw.designpattern.state.subject;

import java.util.ArrayList;
import java.util.List;

// 抽象目标
public abstract class Subject {
    protected List<Observer> observers = new ArrayList<Observer>();

    // 增加观察者方法
    public void add(Observer observer) {
        observers.add(observer);
    }

    // 删除观察者方法
    public void remove(Observer observer) {
        observers.remove(observer);
    }

    // 通知观察者方法
    public void notifyObserver(Long taskId) {
        for (Observer observer : observers) {
            observer.response(taskId);
        }
    }
}