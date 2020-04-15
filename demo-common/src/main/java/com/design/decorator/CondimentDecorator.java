package com.design.decorator;

/**
 * 调料装饰类  需要继承饮料父类 从而达到类型一致。
 * @Date 2019/8/20 9:22
 */
public abstract class CondimentDecorator extends Beverage{

    @Override
    public abstract String getDescription();
}
