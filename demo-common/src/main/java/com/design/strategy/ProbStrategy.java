package com.design.strategy;

/**
 * 具体的策略
 * @Date 2019/7/10 11:44
 */
public class ProbStrategy implements Strategy {
    @Override
    public Hand nextHand() {
        return Hand.getHand(1);
    }

    @Override
    public void study(boolean win) {
        System.out.println("那谁 你赢了！");
    }
}
