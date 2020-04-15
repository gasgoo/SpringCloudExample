package com.common;


import com.lock.BoundedQueue;
import com.lock.CountDownLatchDemo;
import com.lock.TwinsLock;
import org.junit.Test;
import reactor.fn.timer.HashWheelTimer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

/**
 * @TODO
 * @Date 2019/12/4 11:29
 * @name TwinsLockTest
 */


public class TwinsLockTest {


    @Test
    public void test() throws InterruptedException {

        Lock lock = new TwinsLock();
        class Worker extends Thread{

            public void run(){
                while (true){
                    lock.lock();
                    try{
                       Thread.sleep(1000);
                        System.out.println(">>>>>>"+Thread.currentThread().getName());
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                }
            }
        }
        //开启10个线程
        for(int i=0;i<10;i++){
            Worker w=new Worker();
            w.setDaemon(true);
            w.start();
        }
        //没间隔1秒执行
        for(int i=0;i<10;i++){
            Thread.sleep(1000);
            System.out.println();
        }
    }

    @Test
    public void testAdd(){
        BoundedQueue<String> queue =new BoundedQueue<>(3);
        queue.add("niwodai");
        queue.add("ppdai");
        queue.add("renrendai");
        queue.add("magfin");
        queue.remove();
        System.out.println("queue size>>>>>"+queue.getSize());

        System.out.println(queue.getObject(1));
    }

}
