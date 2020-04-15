package com.design.adapter;

/**
 *  适配器模式  类原有的功能  已存在的类
 * @Date 2019/7/2 18:13
 */
public class Banner {

    private String  string;
    public Banner (String string){
        this.string=string;
    }

    public void showWithParen(){
        System.out.println("("+string+")");
    }

    public void showWithAster(){
        System.out.println("*"+string+"*");
    }
}
