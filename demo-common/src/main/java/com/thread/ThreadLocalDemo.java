package com.thread;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.List;

/**
 * @Date 2019/12/26 15:35
 * @name ThreadLocalDemo
 */


public class ThreadLocalDemo implements Runnable {

 public static InheritableThreadLocal<String> inheritableThreadLocal=new InheritableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        inheritableThreadLocal.set("父线程变量赋值");
        ThreadLocalDemo demo=new ThreadLocalDemo();
        Thread t=new Thread(demo);
        t.start();
        Thread.sleep(1000);
        System.out.println("main end");

        ClassLoader app=ClassLoader.getSystemClassLoader();
        System.out.println("classLoad:>>"+app);
        System.out.println(">>>>>>"+t.getContextClassLoader());
        System.out.println(">>>>>>"+ThreadLocalDemo.class.getClassLoader().getClass().getName());

        String s="hello";
        String s1=new String("hello");
        String s2=s1;
        System.out.println(s==s1);
        System.out.println(s1==s2);
        System.out.println(s.equals(s1));
    }

    @Override
    public void run() {
        System.out.println("子线程开始");
        System.out.println("子线程获取父线程的值:"+inheritableThreadLocal.get());
    }
}
