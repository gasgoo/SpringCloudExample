package com.design.decorator;

/**
 * 具体的 饮料类  具体的被装饰者
 * @Date 2019/8/20 9:24
 */
public class HouseBlend extends Beverage {

    public HouseBlend(){
        description="你喜欢的奶茶";
    }

    @Override
    public double cost() {
        return 28;
    }


}
