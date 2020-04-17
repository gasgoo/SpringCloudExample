package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Date 2019/12/26 15:35
 * @name ThreadLocalDemo
 */


public class ThreadLocalDemo implements Runnable {

 public static InheritableThreadLocal<String> inheritableThreadLocal=new InheritableThreadLocal<>();

 public  static  ThreadLocal threadLocal=new ThreadLocal();

 public static ExecutorService executorService= Executors.newFixedThreadPool(5);
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        inheritableThreadLocal.set("父线程变量赋值");
        ThreadLocalDemo demo=new ThreadLocalDemo();
        Thread t=new Thread(demo);
        t.start();
        System.out.println("runing useMemery:"+ (Runtime.getRuntime().freeMemory()/1024/1024)+"m");
        Thread.sleep(1000);
        Thread t1=new Thread(demo);
        t1.start();
        for(int i=0;i<600;i++){
            executorService.execute(t1);
        }
        System.out.println("main end useMemery:"+ (Runtime.getRuntime().freeMemory()/1024/1024)+"m");
        threadLocal.remove();

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
}
