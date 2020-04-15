package com.design.observer;

/**
 * @Description 观察者模式测试类
 * @Author admin
 * @Date 2020/4/13 22:56
 */
public class ObserverPatternDemo {

    public static void main(String[] args) {
        Subject subject = new Subject();
        new RealObserver(subject);
        new OctalObserver(subject);
        System.out.println("first State change:15");
        subject.setState(15);
        System.out.println("second State change:26");
        subject.setState(26);
    }
}
