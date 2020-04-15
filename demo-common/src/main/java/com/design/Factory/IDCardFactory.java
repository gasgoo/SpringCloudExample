package com.design.Factory;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 创建IDCard对象的工厂方法类
 * @Date 2019/7/8 10:24
 */
public class IDCardFactory extends Factory {

    private List owners= Lists.newArrayList();
    @Override
    public Product createProduct(String owner) {
        return new IDCard("test");
    }

    @Override
    public void registerProduct(Product product) {
        owners.add(product.getOwner());
    }

    public List getOwners(){
        return owners;
    }
}
