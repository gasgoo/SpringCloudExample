package com.design.proxy;

/**
 * 代理模式中的真实实现
 * @Date 2019/8/19 15:21
 */
public class Printer implements Printable {

    private String realName;

    public Printer(String name){
        this.realName=name;
        heavyJob("代理模式中的真实实现..."+name);
    }

    @Override
    public void setPrinterName(String name) {
            this.realName=name;
    }

    @Override
    public String getPrinterName() {
        return realName;
    }

    @Override
    public void print(String string) {
        System.out.println("========"+ string +"======");
        System.out.println(string);
    }
    private void heavyJob(String msg){
        System.out.println(msg);
        for(int i=0;i<5;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("game over");
    }
}
