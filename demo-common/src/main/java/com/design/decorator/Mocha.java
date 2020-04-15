package com.design.decorator;

/**
 * 具体的装饰者  具体的调料  面向客户的
 * @Date 2019/8/20 9:28
 */
public class Mocha extends CondimentDecorator {

    Beverage beverage;
    double myCost=25;

    public Mocha(Beverage beverage){
        this.beverage=beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription()+"-Mocha";
    }

    @Override
    public double cost() {
        return beverage.cost()+myCost;
    }
}
