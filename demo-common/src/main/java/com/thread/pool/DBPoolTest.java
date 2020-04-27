package com.thread.pool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date 2020/4/23 15:54
 * @name DBPoolTest
 */


public class DBPoolTest {
    static DBPool pool=new DBPool(100);
    static CountDownLatch latch;

    public static void main(String[] args) throws InterruptedException {
        int threadCount=50;
        latch=new CountDownLatch(threadCount);
        //每个线程操作20次
        int count =20;
        //计数器 统计可以拿到连接的线程
        AtomicInteger got=new AtomicInteger();
        AtomicInteger notget=new AtomicInteger();
        for(int i=0;i<threadCount;i++){
            Thread thread=new Thread(new Worker(count,got,notget));
            thread.start();
        }
        //main线程等待
        latch.await();
        System.out.println("总共尝试了: " + (threadCount * count));
        System.out.println("拿到连接的次数：  " + got);
        System.out.println("没能连接的次数： " + notget);
    }

    static class Worker implements Runnable {
        int           count;
        AtomicInteger got;
        AtomicInteger notGot;

        public Worker(int count, AtomicInteger got,
                      AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }
        @Override
        public void run() {
            while (count > 0) {
                try {
                    // 从线程池中获取连接，如果1000ms内无法获取到，将会返回null
                    // 分别统计连接获取的数量got和未获取到的数量notGot
                    Connection connection = pool.fetchConnection(100);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.release(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                        System.out.println(Thread.currentThread().getName()
                                +"等待超时!");
                    }
                } catch (Exception ex) {
                } finally {
                    count--;
                }
            }
            latch.countDown();
        }
    }
}
