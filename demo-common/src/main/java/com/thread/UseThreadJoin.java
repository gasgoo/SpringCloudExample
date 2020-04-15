package com.thread;

/**
 * 线程 join
 * @Date 2020/4/9 19:11
 * @name UseThreadJoin
 */


public class UseThreadJoin implements Runnable{

    private  Thread thread;
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"线程运行中.");
    }

    static class OtherThread implements Runnable{
        private  Integer i;

        @Override
        public void run() {
            System.out.println("OtherThread running...");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        UseThreadJoin use=new UseThreadJoin();
        OtherThread other=new OtherThread();
        Thread t2=new Thread(other);
        t2.start();
        Thread t1=new Thread(use);
        t1.start();
        t2.join();

    }
}
