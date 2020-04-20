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
                    try {
                        System.out.println("队列已经满!");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Random random=new Random();
                int num=random.nextInt()*1000;
                queue.add(num);
                queue.notifyAll();
            }
        }
    }
}
