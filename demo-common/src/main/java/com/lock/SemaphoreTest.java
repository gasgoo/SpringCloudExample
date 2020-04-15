package com.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @TODO
 * @Date 2020/3/18 16:56
 * @name SemaphoreTest
 */


public class SemaphoreTest {

    private static  final int THREAD_COUNT=30;

    private static ExecutorService pool= Executors.newFixedThreadPool(THREAD_COUNT);

    //许可证量 同时容许10个线程
    private static Semaphore s=new Semaphore(10);

    public static void main(String[] args) {
        for(int i=0;i<THREAD_COUNT;i++){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        s.acquire();
                        System.out.println("SemaphoreTest Thread"+Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(2000);
                        s.release();
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }

                }
            });

        }
        pool.shutdownNow();
    }
}
