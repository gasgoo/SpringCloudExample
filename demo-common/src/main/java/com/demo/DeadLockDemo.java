package com.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2019/9/10 10:27
 */
public class DeadLockDemo {

    private static String A="A";
    private static String B="B";

    public static void main(String[] args) {
            new DeadLockDemo().deadLock();
    }



    private void deadLock(){
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("死锁中。。。。。。");
                synchronized (A){
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                synchronized (B){
                    System.out.println("第一个线程："+Thread.currentThread().getName()+"====B");
                }
            }
        });
        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B){
                    synchronized (A){
                        System.out.println("===第二个线程:"+Thread.currentThread().getName());
                    }
                }
            }
        });
        thread1.start();
        t2.start();

    }

}
