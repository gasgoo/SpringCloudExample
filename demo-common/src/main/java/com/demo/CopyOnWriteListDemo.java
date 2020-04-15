package com.demo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @Date 2019/11/22 11:01
 * @name CopyOnWriteListDemo
 */


public class CopyOnWriteListDemo implements Runnable {

    private volatile static int ticket = 10;
    private final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteListDemo demo = new CopyOnWriteListDemo();
        Thread t1 = new Thread(demo);
        t1.start();
        Thread t2 = new Thread(demo);
        t2.start();
        Thread t3 = new Thread(demo);
        t3.start();
        demo.testArrayBlockingQueue();
        int a=1;

        System.out.println(">>>>"+ (a<<2));
    }

    public void addEle() {
        CopyOnWriteArrayList<Object> list = Lists.newCopyOnWriteArrayList();
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            list.add(i);
            sum = sum + i;
        }
        System.out.println("CurrentThreadName: " + Thread.currentThread().getName() + ":" + sum);
        list.stream().forEach(System.out::println);
    }

    public void addList() {
        ArrayList<Object> list = Lists.newArrayList();
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            list.add(i);
            sum = sum + i;
        }
        System.out.println("CurrentThreadName: " + Thread.currentThread().getName() + ":" + sum);
        list.stream().forEach(System.out::println);
    }

    //ConcurrentHashMap通过锁 、转移节点 自旋 Cas机制等 实现线程安全
    //线程安全的地方：map初始化的时候，新增元素put方法的时候，扩容的时候
    public void addMap() throws InterruptedException {
        ConcurrentMap<Object, Object> map = Maps.newConcurrentMap();
        map.put("1", "niwodai");
        map.put("2", "ppdai");
        map.put("3", "renrendai");
        map.put("4", "niwodai");
        map.put("5", "ppdai");
        map.put("6", "renrendai");
        map.put("7", "niwodai");
        map.put("8", "ppdai");
        map.put("9", "renrendai");
        map.put("9", "renrendai");
        System.out.println(map.get("1"));

    }

    public void testArrayBlockingQueue() throws InterruptedException {
        List<String> list = ImmutableList.of("a", "b", "c");
        ArrayBlockingQueue q = new ArrayBlockingQueue(4, true, list);
        System.out.println("before: " + q.size());
        Object take = q.take();
        System.out.println("===" + take);
        q.put("hello");
        System.out.println("after: " + q.size());
        while (q.iterator().hasNext()) {
            System.out.println(q.take());
        }
        SynchronousQueue synchronousQueue = new SynchronousQueue();
        DelayQueue delayQueue = new DelayQueue();
    }

    public void ticketCount() {
        try {
            lock.lock();
            lock.newCondition();
            while (true) {
                if (ticket == 0) {
                    System.out.println("no tikcet");
                    return;
                }
                ticket--;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "购买,余票:" + ticket);
            }
        } finally {
            lock.unlock();
        }

    }

    @Override
    public void run() {
        //addEle();
        //addList();
        //ticketCount();
        try {
            addMap();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
