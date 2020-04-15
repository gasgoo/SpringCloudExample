package com.design.adapter;

/**
 * 具体的适配器角色  具有原有的功能和本次客户需要的功能
 * @Date 2019/7/2 18:26
 */
public class PrintBanner extends Banner implements Print{


    public PrintBanner(String string) {
        super(string);
    }

    @Override
    public void printWeak() {
        System.out.println("适配器转换后的功能输出...此处可以扩展客户需要的功能");
        showWithParen();
    }

    @Override
    public void printStrong() {
        System.out.println("===新的符合客户需要的功能");
        showWithAster();
    }

    public static void main(String[] args) {
        Print p=new PrintBanner("hello,适配器模式!");
        p.printWeak();
        p.printStrong();
    }
}
