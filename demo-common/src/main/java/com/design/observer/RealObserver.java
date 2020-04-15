package com.design.observer;

/**
 * @Description 实际的观察者
 * @Author admin
 * @Date 2020/4/13 22:40
 */
public class RealObserver extends Observer {


    public RealObserver(Subject subject) {
        this.subject = subject;
        this.subject.attch(this);
    }

    @Override
    public void update() {
        System.out.println("RealObserver观察的事件发生了>>>>state:" + Integer.toBinaryString(subject.getState()));

    }
}
