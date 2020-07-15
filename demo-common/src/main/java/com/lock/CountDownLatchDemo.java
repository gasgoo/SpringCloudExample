package com.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @TODO
 * @Date 2019/12/5 18:10
 * @name CountDownLatchDemo
 */


public class CountDownLatchDemo {

    class Worker implements Runnable {

        private final CountDownLatch start;

        private final CountDownLatch done;

        Worker(CountDownLatch startSignal, CountDownLatch donwSignal) {
            this.start = startSignal;
            this.done = donwSignal;
        }

        @Override
        public void run() {
            System.out.println(">>>>>" + Thread.currentThread().getName());
            try {
                done.countDown();
                start.await();
                doWorker();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(">>>>同时执行的逻辑.....do end");
        }

        void doWorker() throws InterruptedException {
            System.out.println("doWorkder>>>>>" + Thread.currentThread().getName() + "waiting.....");

        }
    }


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch start =new CountDownLatch(1);
        CountDownLatch done= new CountDownLatch(9);
        CountDownLatchDemo demo=new CountDownLatchDemo();
        Worker worker = demo.new Worker(start, done);

        for(int i=0;i<9;i++){
            new Thread(worker).start();
        }
        System.out.println("main thread begin");
        start.countDown();
        done.await();
        System.out.println("main thread end");
    }


}
