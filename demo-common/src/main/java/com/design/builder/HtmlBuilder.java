package com.design.builder;

/**
 * @TODO
 * @Date 2019/7/1 15:48
 */
public class HtmlBuilder extends Builder {
    @Override
    public void makeTitle(String title) {
        System.out.println("创建的html文件名称:"+title+".html");
    }

    @Override
    public void makeString(String str) {
        System.out.println("content......"+str);
    }

    @Override
    public void close() {
        System.out.println("game over");
    }
}
