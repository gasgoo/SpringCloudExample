package com.thread;

import java.util.Queue;
import java.util.Random;

/**
 * wait
 * @Date 2020/4/20 18:13
 * @name Produer
 */


public class Produer implements Runnable {

    private Queue<Integer> queue;
    private int maxSize;

    public Produer(Queue<Integer> queue, int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while(true){
            synchronized (queue){
                while(queue.size() == maxSize){
                    long start=System.currentTimeMillis();
                    try {
                        System.out.println("队列已经满!");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long end =System.currentTimeMillis();
                    System.out.println("生产者等待:"+(end-start)+"秒");
                }

                Random random=new Random(100);
                int num=random.nextInt()+1;
                queue.add(num);
                queue.notifyAll();
            }
        }
    }
}
