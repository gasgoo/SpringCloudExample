package com.design.bridge;

/**
 * 具体功能的实现
 * @Date 2019/7/8 17:53
 */
public class StringDisplayImpl extends DisplayImpl{

    private String string;
    public  StringDisplayImpl(String string){
        this.string=string;
    }
    @Override
    public void rawOpen() {
        printline();
    }

    @Override
    public void rawPrint() {
        System.out.println("===="+string+"====");
    }

    @Override
    public void rawClose() {
        printline();
    }

    private void printline(){
        System.out.print("+");
        for(int i=0;i<5;i++){
            System.out.println("==");
        }
        System.out.println("+");

    }
}
