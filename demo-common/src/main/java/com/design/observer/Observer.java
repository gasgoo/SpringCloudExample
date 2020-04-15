package com.design.observer;

/**
 * @Description 观察者 观察到事件则触发修改动作
 * @Author admin
 * @Date 2020/4/13 22:27
 */
public abstract class Observer {

    protected Subject subject;

    public abstract void update();
}
