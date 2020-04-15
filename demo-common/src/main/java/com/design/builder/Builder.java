package com.design.builder;

/**
 * 构建者模式中的具体构建者
 * @Date 2019/7/1 15:32
 */
public abstract class Builder {

    public abstract  void makeTitle(String title);
    public abstract  void makeString(String str);
    public abstract void close();
}
