package com.design.observer;

import org.assertj.core.util.Lists;

import java.util.List;

/**
 * @Description 被观察者 持有观察者 事件发生了通知观察者
 * @Author admin
 * @Date 2020/4/13 22:35
 */
public class Subject {

    private List<Observer> observers = Lists.newArrayList();

    private int state;

    public void setState(int state) {
        this.state = state;
        notifyAllObserviers();
    }

    public int getState() {
        return state;
    }

    public void attch(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObserviers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

}
