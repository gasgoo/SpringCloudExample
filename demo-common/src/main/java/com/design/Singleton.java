package com.design;

/**
 * @Date 2019/7/1 10:01
 */
public class Singleton implements Runnable {

    //编译重排 指令顺序问题
    private volatile static  Singleton singleton = null;

    private Singleton() {
        System.out.println("单例模式双重检查");
    }

    //同步是防止多线程间切换导致的空指针
    public static Singleton getInstance() {
        if(singleton==null){
            synchronized (Singleton.class){
                if(singleton==null){
                    singleton=new Singleton();
                }
            }
        }
        return singleton;
    }


    public static void main(String[] args) {
        Singleton s1 = new Singleton();
        Singleton s2 = new Singleton();
        Thread t1=new Thread(s1);
        Thread t2=new Thread(s2);
        Thread t3=new Thread(s2);
        t1.start();
        t2.start();
        t3.start();

    }


    @Override
    public void run() {
        Singleton instance = getInstance();
        System.out.println(Thread.currentThread().getName()+"=="+instance.toString());

    }
}
