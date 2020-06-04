package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date 2019/12/26 15:35
 * @name ThreadLocalDemo
 */


public class ThreadLocalDemo implements Runnable {

 public static InheritableThreadLocal<String> inheritableThreadLocal=new InheritableThreadLocal<>();

 public  static  ThreadLocal threadLocal=new ThreadLocal();

 public static ExecutorService executorService= Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalDemo demo=new ThreadLocalDemo();
        for(int i=0;i<=3;i++){
            System.out.println(threadLocal.get());
        }

    }
    static class localVariable{
        private byte[] a=new byte[1024*1024*5];
    }

    @Override
    public void run() {
        System.out.println("子线程开始");
        System.out.println("子线程获取父线程的值:"+inheritableThreadLocal.get());
        threadLocal.set("threalLocal set method!  threadName:"+Thread.currentThread().getName());
        threadLocal.set(new localVariable());
        System.out.println(threadLocal.get());

    }

    public void test(){
        Integer reTryTimes=new Integer(1);
        reTryTimes++;
        //System.out.println("======="+threadLocal.get());
    }
}
