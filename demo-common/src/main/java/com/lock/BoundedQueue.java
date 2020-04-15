package com.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  有界队列 通过Condition接口实现阻塞
 * @Date 2019/12/5 9:33
 * @name BoundedQueue
 */


public class BoundedQueue<T> {


    private Object[] items;

    private int addIndex=0,removeIndex=0,count=0;

    private Lock lock =new ReentrantLock();

    private Condition notEmpty =lock.newCondition();
    private Condition notFull =lock.newCondition();

    public BoundedQueue(int size){
        items =new Object[size];
    }


    public void add(T t){
        lock.lock();
        try{
            //队列已经满 则进入等待阻塞 直到有空位
            while(count ==items.length){
                notFull.await();
                System.out.println("队列 full了，等待....");
                if(addIndex == items.length){
                    items[addIndex] =t;
                    addIndex++;
                    count=items.length;
                    notEmpty.signal();

                    System.out.println("add success!");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public T remove(){
        lock.lock();
        Object x =null;
        try{
            //队列为空则取线程进入等待
            while(count ==0){
                notEmpty.await();
                System.out.println("remove await");
                if(removeIndex == items.length){
                    x= items[removeIndex];
                    count=items.length;
                    removeIndex++;
                    //取出了元素 有位置了可以继续添加 唤醒add线程
                    notFull.signal();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return (T) x;
    }

    public int getSize(){
        return items.length;
    }

    public Object getObject(int index){
        return items[index];
    }
}
