package com.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 链表阻塞队列
 * @Date 2019/11/28 10:43
 * @name DiyLinkedQueue
 */

@Slf4j
public class DiyLinkedQueue<T> implements DiyQueue<T>{

    //队列头
    private volatile Node<T> head;
    //队列尾
    private volatile Node<T> tail;

    //队列中的元素
    class DIYNode extends Node<T>{

        public DIYNode(T item) {
            super(item);
        }
    }


    /**
     * 长度
     */
    private AtomicInteger size=new AtomicInteger();
    /**
     * 容量 固定不能修改
     */
    private final Integer capacity;
    /**
     * 放数据锁
     */
    private ReentrantLock putLock = new ReentrantLock();

    /**
     * 拿数据锁
     */
    private ReentrantLock takeLock = new ReentrantLock();

    @Override
    public boolean put(T item) {
        if(item ==null){
            return false;
        }
        //尝试加锁
        try {
            boolean lockSuccess = putLock.tryLock(300, TimeUnit.MILLISECONDS);
            if(!lockSuccess){
                return false;
            }
            if(size.get()>capacity){
                System.out.println("队列已经满!");
                return false;
            }
            //添加到队列尾部
            tail=tail.next=new DIYNode(item);
            size.incrementAndGet();
            return true;
        } catch (InterruptedException e) {
            System.out.println("获取锁超时!");
            return false;
        }catch (Exception e){
            System.out.println("队列添加元素异常!"+e);
            return false;
        }finally {
            putLock.unlock();
        }

    }

    /**
     * @Description 取数据
     * @Date 2019/11/28 10:38
     **/
    @Override
    public T take() {
        if(size.get()==0){
            return null;
        }
        try {
            boolean takeSuccess = takeLock.tryLock(200, TimeUnit.MILLISECONDS);
            if(!takeSuccess){
               throw new RuntimeException("获取take锁失败!");
            }
            //把头节点的下一个元素拿出
            Node expectHead=head.next;
            T result = head.item;
            //head节点的值取出了 赋值null
            head.item=null;
            //重新设置头节点
            head=expectHead;
            size.decrementAndGet();
            return result;
        } catch (InterruptedException e) {
            System.out.println("tryLock 200 timeOut"+e);
        }catch (Exception e){
            System.out.println("take error"+e);
        }finally {
            takeLock.unlock();
        }
        return null;
    }


    public DiyLinkedQueue(){
        capacity=Integer.MAX_VALUE;
        head=tail=new DIYNode(null);
    }
    //有参构造器
    public DiyLinkedQueue(Integer capacity) {
        // 进行边界的校验
        if(null == capacity || capacity < 0){
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        head = tail = new DIYNode(null);
    }

    @Override
    public int getSize(){
        return size.get();
    }


}
