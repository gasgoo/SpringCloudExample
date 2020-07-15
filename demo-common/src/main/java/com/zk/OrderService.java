package com.zk;

/**
 * @Date 2020/7/10 15:25
 **/
//使用多线程模拟生成订单号
public class OrderService implements Runnable {
    private OrderNumGenerator orderNumGenerator = new OrderNumGenerator();

    @Override
    public void run() {
        getNumber();
    }

    public void getNumber() {
        String number = orderNumGenerator.getNumber();
        System.out.println(Thread.currentThread().getName() + "num" + number);
    }

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        //开启50个线程
        for (int i = 0; i < 50; i++) {
            new Thread(orderService).start();
        }


    }
}