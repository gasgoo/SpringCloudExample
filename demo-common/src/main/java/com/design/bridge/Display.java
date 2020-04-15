package com.design.bridge;

/**
 * 桥接模式中的 具体功能结构 使用委托的方法而不是 继承的方法 具体执行者是 DisplayImpl的实现
 *
 * 桥接模式 抽象部分和实现分离，都可以独立的变化，连接类的功能层次结构和实现层次结构  统一接口
 * @Date 2019/7/8 17:44
 */
public class Display {

    public Display(){

    }
    private DisplayImpl impl;
    public Display(DisplayImpl impl){
        this.impl=impl;
    }
    public void open(){
        impl.rawOpen();
    }
    public void print(){
        impl.rawOpen();
    }
    public void close(){
        impl.rawClose();
    }
}
