package com.zk;

import org.I0Itec.zkclient.ZkLock;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;


/**
 * @Date 2020/7/10 15:25
 **/
//使用多线程模拟生成订单号
public class OrderService implements Runnable {
    private OrderNumGenerator orderNumGenerator = new OrderNumGenerator();

    private static CountDownLatch countDownLatch = new CountDownLatch(50);

    private static List<String> result = new Vector<String>();
    private Lock lock = new ZkLockUtils();

    @Override
    public void run() {
        try {
            countDownLatch.await();
            result.add(orderNumGenerator.getNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getNumber() {
        try {
            lock.tryLock();
            String number = orderNumGenerator.getNumber();
            System.out.println(Thread.currentThread().getName() + ",生成订单ID:" + number);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("####生成唯一订单号###");

        OrderService orderService = new OrderService();
        for (int i = 0; i < 50; i++) {
            new Thread(orderService).start();
            countDownLatch.countDown();
        }


        Thread.sleep(1000);

        Collections.sort(result);
        for (String str : result) {
            System.out.println(str);
        }

    }
}