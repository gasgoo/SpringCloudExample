package com.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @ 用同步器 AbstractQueuedSycnhronizer实现共享锁
 * 本例 可以两个线程同时获取锁
 * @Date 2019/12/4 11:04
 * @name TwinsLock
 */


public class TwinsLock implements Lock {

    private final Sync sync = new Sync(2);

    private static class Sync extends AbstractQueuedSynchronizer {

        Sync(int count) {
            if (count <= 0) {
                throw new RuntimeException("count 需要大于0");
            }
            setState(count);
        }
        //加锁的方法
        public boolean acquireByShared(int reduceCount){
            for(;;){
                if(reduceCount<0){
                    return false;
                }
                int current =getState();
                int newCount=current -reduceCount;
                if(current<0 ||newCount<0){
                    return false;
                }
                if(compareAndSetState(current,newCount)){
                    return true;
                }

            }

        }

        @Override
        public int tryAcquireShared(int reduceCount) {
            for (; ; ) {
                int current = getState();
                //最新可用资源数
                int newCount = current - reduceCount;
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }

        @Override
        public boolean tryReleaseShared(int returnCount) {
            for(;;){
                int current = getState();
                int newCount =current +returnCount;
                if(compareAndSetState(current,newCount)){
                    return true;
                }
            }
        }

    }

    @Override
    public void lock(){
        sync.acquireShared(1);
    }

    @Override
    public boolean tryLock(){
        int count = sync.tryAcquireShared(1);
        if(count>0){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public void unlock(){
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }


    public boolean hasQueuedThreads(){
        return sync.hasQueuedThreads();
    }

    @Override
    public boolean tryLock(long timeOut, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1,unit.toNanos(timeOut));
    }


    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }


}
