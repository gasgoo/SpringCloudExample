package com.design.Factory;


/**
 * 身份证的产品
 * @Date 2019/7/8 10:21
 */
public class IDCard extends Product {

    private String owner;
    IDCard(String owner){
        System.out.println("create"+owner+" is idcard");
        this.owner=owner;
    }

    @Override
    public void use() {
        System.out.println("使用身份证");
    }

    @Override
    public String getOwner(){
        return owner;
    }

}
