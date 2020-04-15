package com.design.strategy;

import java.util.Random;

/**
 * 具体的策略
 * @Date 2019/7/10 11:10
 */
public class WinningStrategy implements Strategy{

    private Random random;
    private boolean won=false;
    //上一次的手势
    private Hand preHand;

    /*如果上一局输了就随机给个手势*/
    @Override
    public Hand nextHand() {
        if(!won){
            preHand=Hand.getHand(random.nextInt(3));
        }
        return preHand;
    }

    @Override
    public void study(boolean win) {
        won=win;
    }


}
