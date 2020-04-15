package com.design.strategy;

/**
 * 手势
 * @Date 2019/7/10 10:01
 */
public class Hand {
    public static final int GUU=0;//石头
    public static final int CHO=1;//剪刀
    public static final int PAA=2; //布

    public int handValue;
    public static final String[] name={"石头","剪刀","布"};
    public static final Hand[] hand={
            new Hand(GUU),
            new Hand(CHO),
            new Hand(PAA),
    };

    public Hand(int handValue){
        this.handValue=handValue;
    }

    public static Hand getHand(int handValue){
        return hand[handValue];
    }
    /*比较大小 */
    public  int fight(Hand hand){
        if(this==hand){
            return 0;
        }else if((this.handValue + 1) % 3==hand.handValue){
            return 1;
        }else{
            return -1;
        }
    }
    public boolean isBigThan(Hand h){
        return fight(h)==1;
    }
    //如果this输给了H 则返回true
    public boolean isLessThan(Hand h){
        return fight(h)==-1;
    }

    @Override
    public String toString(){
        return name[handValue];
    }
}
