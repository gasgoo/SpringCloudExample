package com.proxy;

/**
 * @Date 2019/8/28 21:13
 */
public class SynchronizedClass implements Runnable {

    static SynchronizedClass instance1=new SynchronizedClass();
    static SynchronizedClass instance2=new SynchronizedClass();
    static  int i=0;
    @Override
    public void run() {
        calc();
    }

    public  static synchronized void method(){
        System.out.println("类锁 static形式"+Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"执行结束!");
    }

    public  void calc(){
        System.out.println("当前线程"+Thread.currentThread().getName());
        synchronized(SynchronizedClass.class) {
            for (int j = 0; j < 100000; j++) {
                i++;
            }
        }
        System.out.println("result==="+i);
        System.out.println(Thread.currentThread().getName()+"执行结束!");

    }

    public static void main(String[] args) {

        Thread thread1=new Thread(instance1);
        Thread thread2=new Thread(instance2);
        thread1.start();
        thread2.start();
        while(thread1.isAlive()||thread2.isAlive()){

        }
        System.out.println("finised!");
    }
}
