package com.design.decorator;

/**
 *
 * @Date 2019/11/6 10:17
 * @name DecoratorInvoice
 */


public class DecoratorInvoice extends Invoice {

    protected Invoice ticket;
    public DecoratorInvoice(Invoice t){
        this.ticket=t;
    }

    @Override
    public void printInvoice(){
        if(ticket!=null){
            ticket.printInvoice();
        }
    }
}
