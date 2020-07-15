package com.zk;

import java.util.List;
import java.util.Vector;


/**
 * @Date 2020/7/10 15:25
 **/
//使用多线程模拟生成订单号
public class OrderLockService implements Runnable {
    private OrderNumGenerator orderNumGenerator = new OrderNumGenerator();


    private ExLock lock = new ZkLocker();

    @Override
    public void run() {
        try {
            getNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getNumber() {
        try {
            lock.lock();
            String number = orderNumGenerator.getNumber();
            System.out.println(Thread.currentThread().getName() + ",生成订单ID:" + number);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unLock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("####生成唯一订单号###");

        OrderLockService orderLockService = new OrderLockService();
        for (int i = 0; i < 50; i++) {
            new Thread(orderLockService).start();
        }


    }
}