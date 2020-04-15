package com.design.builder;

/**
 * 构造器模式中的指挥者
 * @Date 2019/7/1 15:36
 */
public class Director {
    private Builder builder;
    public Director(Builder builder){
        this.builder=builder;
    }

    public void construct(){
        builder.makeTitle("构建地基部分开始");
        builder.makeString("开始装修");
        builder.makeString("装修完成");
        builder.close();
    }
}
