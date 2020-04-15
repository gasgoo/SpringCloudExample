package com.design.bridge;

/**
 * 桥接模式中 定义功能中对应的方法
 * @Date 2019/7/8 17:46
 */
public abstract class DisplayImpl {
    public abstract void rawOpen();
    public abstract void rawPrint();
    public abstract void rawClose();
}
