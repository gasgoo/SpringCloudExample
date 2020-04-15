package com.design.Factory;


/**
 * 工厂方法模式中的 模板模式
 * @Date 2019/7/8 10:18
 */
public abstract class Factory {

    public final Product create(String owner){
        Product p=createProduct(owner);
        registerProduct(p);
        return p;
    }

    public abstract Product createProduct(String owner);
    public abstract void registerProduct(Product product);
}
