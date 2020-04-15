package com.design.proxy;

/**
 * @TODO
 * @Date 2019/8/19 15:47
 */
public class ProxyTest {

    public static void main(String[] args) {
        Printable printable=new PrinterProxy("张三丰");
        System.out.println("now name==="+printable.getPrinterName());
        printable.setPrinterName("张无忌");
        System.out.println("===="+printable.getPrinterName());
        printable.print("武林至尊 宝刀屠龙!");
    }
}
