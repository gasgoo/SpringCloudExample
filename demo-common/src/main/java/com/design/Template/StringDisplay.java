package com.design.Template;

/**
 * 模板模式  子类实现父类的方法
 * @Date 2019/7/8 9:50
 */
public class StringDisplay extends AbstractDisplay {

    private String string;
    private int width;
    public StringDisplay(String string,int width){
        this.string=string;
        this.width=width;
    }

    @Override
    public void open() {
        System.out.println("open<<<<");
    }

    @Override
    public void print() {
        System.out.println(string);
    }

    @Override
    public void close() {
        System.out.println(">>>>close");
    }

    public static void main(String[] args) {
        AbstractDisplay d1=new StringDisplay("hello",4);
        d1.display();
    }
}
