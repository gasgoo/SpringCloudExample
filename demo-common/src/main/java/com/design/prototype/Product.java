package com.design.prototype;

/**
 * 原型模式的接口  定义复制实例的方法  必须继承 Cloneable接口
 * @Date 2019/7/1 11:22
 */
public interface Product extends Cloneable{
    public abstract void use(String s);
    public abstract Product createClone();
}
