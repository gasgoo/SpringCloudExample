package com.design.decorator;

/**
 * 装饰者模式中的 顶层抽象类  包含饮料的公用方法 根据原料计算价格
 * @Date 2019/8/20 9:18
 */
public abstract class Beverage {

    String description="未知饮料";
    public String getDescription(){
        return description;
    }

    public abstract double cost();

    public void context(){
        System.out.println("这里是发票的内容!");
    }
}
