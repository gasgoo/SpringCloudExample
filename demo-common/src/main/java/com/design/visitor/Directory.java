package com.design.visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 访问目录
 *
 * @Date 2019/7/16 15:53
 */
public class Directory extends Entry {

    private String name;
    private List<Entry> dir = new ArrayList();


    public Directory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        int size = 0;
        for (Entry entry : dir) {
            size += entry.getSize();
        }
        return size;
    }

    @Override
    public void printList(String prefix) {
        System.out.println(prefix+"/"+this);
                 Iterator it=dir.iterator();
                 Entry entry;
                 while(it.hasNext()){
                         entry=(Entry)it.next();
                        entry.printList(prefix+"/"+name);
                     }
    }

    @Override
    public void accept(AbstractVisitor abstractVisitor) {
        abstractVisitor.visit(this);
    }

    @Override
    public Entry add(Entry entry) {
        dir.add(entry);
        return this;
    }
}
