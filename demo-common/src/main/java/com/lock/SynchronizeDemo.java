package com.lock;

/**
 * @TODO
 * @Date 2020/3/30 17:33
 * @name SynchronizeDemo
 */


public class SynchronizeDemo implements Runnable{

    private static int count=0;
    static Mutex mutex=new Mutex();
    public static void main(String[] args) throws InterruptedException {
        SynchronizeDemo demo=new SynchronizeDemo();
        for(int i=0;i<10;i++){
            Thread t=new Thread(demo);
            Thread t1=new Thread(demo);
            t.start();
            t1.start();
        }
        Thread.sleep(2000);
        System.out.println("count的value:"+count);

    }

    public  void testLock(){
       synchronized (mutex){
            System.out.println("synchronized同步块 lock object");
            count++;
        }
        System.out.println("count的value:"+count);
    }

    public synchronized void testLock1(){
            count++;
    }



    @Override
    public void run() {
        testLock1();
    }
}
