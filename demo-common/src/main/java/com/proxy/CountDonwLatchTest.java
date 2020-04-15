package com.proxy;

import java.util.concurrent.CountDownLatch;

/**
 * @Date 2019/8/15 21:42
 */
public class CountDonwLatchTest {

    private final CountDownLatch latch=new CountDownLatch(2);

    public static void main(String[] args) {
        CountDonwLatchTest countDonwLatchTest=new CountDonwLatchTest();
        countDonwLatchTest.test();
        countDonwLatchTest.test1();

        try {
            System.out.println("等待2个子线程执行完毕...");
            countDonwLatchTest.latch.await();
            System.out.println("子线程执行完毕，继续主线程===");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void test(){
        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                    Thread.sleep(3000);
                    System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    public void test1(){
        new Thread(){
            public void run() {
                try {
                    System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                    Thread.sleep(3000);
                    System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

}
