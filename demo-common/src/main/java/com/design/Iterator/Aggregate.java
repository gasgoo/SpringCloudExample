package com.design.Iterator;


/**
 * 表示集合的接口 只要实现这个接口就能具有聚合的属性
 * @Date 2019/7/2 16:02
 */
public interface Aggregate {

    public abstract Iterator iterator();
}
