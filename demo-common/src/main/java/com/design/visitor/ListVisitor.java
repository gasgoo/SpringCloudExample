package com.design.visitor;

import com.design.Iterator.Iterator;

/**
 * @Date 2019/11/8 10:29
 * @name ListVisitor
 */


public class ListVisitor extends AbstractVisitor {

    String currentDir = "";

    @Override
    public void visit(File file) {
        System.out.println("=====" + file);
    }

    @Override
    public void visit(Directory directory) {
        System.out.println(currentDir + "/" + directory);
        String saveDir = currentDir;
        currentDir += ("/" + directory.getName());
        Iterator it = directory.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            entry.accept(this);
        }
        currentDir = saveDir;
    }
}
