package com.design.decorator;

/**
 * 发票头部
 * @Date 2019/11/6 10:19
 * @name HeadInvoice
 */


public class HeadInvoice extends DecoratorInvoice {
    public HeadInvoice(Invoice ticket){
        super(ticket);
    }

    @Override
    public void printInvoice(){
        System.out.println("This is the header of the invoice");
        ticket.printInvoice();
    }
}
