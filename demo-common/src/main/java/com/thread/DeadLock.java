package com.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @Date 2019/12/2 14:25
 * @name DeadLock
 */

@Slf4j
public class DeadLock {

    private static final Object share1 =new Object();
    private static final Object share2 =new Object();

    public void deadLock() throws InterruptedException {
        Thread thread1 = new Thread(() ->{
            synchronized (share1){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (share2){
                log.info("thread1 {} runing",Thread.currentThread().getName());
            }
        });

        Thread thread2 = new Thread(() ->{
            synchronized (share2){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (share1){
                log.info("thread2 {} runing",Thread.currentThread().getName());
            }
        });
        thread1.start();
        thread2.start();
        Thread.sleep(100000);
        log.info(">>>>>>>>>>",System.getenv());
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLock deadLock = new DeadLock();
        deadLock.deadLock();
    }

}
