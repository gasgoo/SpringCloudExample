package com.design.visitor;

/**
 * 接收访问者的访问接口
 * @Date 2019/7/16 15:48
 */
public interface Element {
    public void accept(AbstractVisitor abstractVisitor);
}
