package com.design.decorator;

/**
 * 发票底部
 * @Date 2019/11/6 10:24
 * @name FootInvoice
 */


public class FootInvoice extends DecoratorInvoice {


    public FootInvoice(Invoice t) {
        super(t);
    }

    @Override
    public void printInvoice(){
        ticket.printInvoice();
        System.out.println("This is the foot of the Invoice");
    }

}
