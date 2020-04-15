package com.design.decorator;

/**
 * 装饰者模式
 * @Date 2019/8/20 9:33
 */
public class DecoratorTest {

    public static void main(String[] args) {
        //printNN();

        Invoice t=new Invoice();
        Invoice ticket=new FootInvoice(new HeadInvoice(t));
        ticket.printInvoice();
        System.out.println("==================");
        Invoice ticket2=new FootInvoice(new HeadInvoice(new DecoratorInvoice(null)));
        ticket2.printInvoice();


    }

    private static void printNN() {
        Beverage beverage=new HouseBlend();
        System.out.println("被装饰对象的属性:  "+beverage.getDescription()+"cost:"+beverage.cost());

        //加入装饰者后的属性
        beverage=new Mocha(beverage);
        System.out.println("装饰后的属性:");
        System.out.println(beverage.getDescription()+"   cost:"+beverage.cost());
    }
}
