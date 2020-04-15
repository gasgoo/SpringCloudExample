package com.design.prototype;

import java.util.HashMap;

/**
 * 通过指定的实例名称来创建一个实例
 * @Date 2019/7/1 11:23
 */
public class Manager {
    /*保存名字和实例的关系*/
    private HashMap showCase=new HashMap();
    public void register(String name,Product product){
        showCase.put(name,product);
    }
    /**
     * @Description 根据实例名称复制一个新的实例
     * @Date 2019/7/1 11:26
     * @Param [protoName]
     * @return com.design.prototype.Product
     **/
    public Product create(String protoName){
        Product p = (Product) showCase.get(protoName);
        return p.createClone();
    }



}
