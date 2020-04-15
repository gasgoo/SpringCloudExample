package com.design.strategy;

/**
 * 策略模式接口类
 * @Date 2019/7/10 11:08
 */
public interface Strategy {

    public abstract Hand nextHand();
    public void study(boolean win);
}
