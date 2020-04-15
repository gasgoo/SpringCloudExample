package com.design.builder;

/**
 * @TODO
 * @Date 2019/7/1 15:39
 */
public class TextBuilder extends Builder{

    @Override
    public void makeTitle(String title) {
        System.out.println("文章题目title: "+title+".txt");
    }

    @Override
    public void makeString(String str) {
        System.out.println("正文部分:"+str);
    }

    @Override
    public void close() {
        System.out.println("文章结尾 over");
    }
}
