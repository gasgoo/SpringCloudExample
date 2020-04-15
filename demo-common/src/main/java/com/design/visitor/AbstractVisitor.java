package com.design.visitor;


/**
 * 访问者抽象类
 * @Date 2019/7/16 15:45
 */
public abstract class AbstractVisitor {

    public abstract void visit(File file);
    public abstract void visit(Directory directory);
}
