package com.design.observer;

/**
 * @Description 实际的观察者
 * @Author admin
 * @Date 2020/4/13 22:40
 */
public class OctalObserver extends Observer {


    public OctalObserver(Subject subject) {
        this.subject = subject;
        this.subject.attch(this);
    }

    @Override
    public void update() {
        System.out.println("OctalObserver观察的事件发生了>>>>state:" + Integer.toOctalString(subject.getState()));

    }
}
