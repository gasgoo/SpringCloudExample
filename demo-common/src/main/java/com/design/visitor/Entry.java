package com.design.visitor;

import com.design.Iterator.Iterator;

/**
 * @TODO
 * @Date 2019/7/16 15:50
 */
public abstract class Entry implements Element {
    public abstract String getName();

    public abstract int getSize();

    public abstract void printList(String prefix);

    public void printList() {
        printList("");
    }

    public Entry add(Entry entry) throws Exception {
        throw new Exception("异常");
    }

    public Iterator iterator() throws RuntimeException {
        throw new RuntimeException();
    }
}
