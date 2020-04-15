package com.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Date 2019/11/26 11:25
 * @name ThreadPoolDemo
 */


public class ThreadPoolDemo implements Runnable{

    public static void main(String[] args) {
        ThreadPoolDemo demo=new ThreadPoolDemo();
        Thread t1=new Thread(demo);
        Thread t2=new Thread(demo);
        Thread t3=new Thread(demo);
        Thread t4=new Thread(demo);
        Thread t5=new Thread(demo);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }


    public void testFixed(){
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        Executors.newCachedThreadPool();
        Executors.newScheduledThreadPool(5);
        executorService.submit(() -> System.out.println(Thread.currentThread().getName()+" is runing"));
        executorService1.submit(() -> System.out.println(Thread.currentThread().getName()+"....runing"));

    }

    @Override
    public void run() {
        System.out.println("===="+Thread.currentThread().getName());
        testFixed();
    }
}
