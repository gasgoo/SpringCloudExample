package com.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 *  同步器AbstractQueueSynchronizer 实现独占锁的实例
 *
 *
 * @Date 2019/12/4 10:12
 * @name Mutex
 */


public class Mutex implements Lock {

    private static class Sync extends AbstractQueuedSynchronizer{

        //是否处于占有状态
        protected  boolean isHeldExcluusively(){
            return getState()==1;
        }


        @Override
        public boolean tryAcquire(int acquires){
            //状态为0则获取锁成功
            if(compareAndSetState(0,1)){
                //设置独占
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //释放锁 状态为0  占有锁的状态是1
        @Override
        protected  boolean tryRelease(int release){
            if(getState() ==0 ){
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        //每个Condition都包含一个Condition队列
        Condition newCondition(){
            return newCondition();
        }
    }

    //Mutex重写Lock方法通过 Sync实现

    private final Sync sync =new Sync();

    @Override
    public void lock(){
        sync.tryAcquire(1);
    }
    @Override
    public boolean tryLock(){
        return sync.tryAcquire(1);
    }
    @Override
    public void unlock(){
        sync.tryRelease(1);
    }

    public boolean isLocked(){
        return sync.isHeldExcluusively();
    }

    public boolean hasQueuedThreads(){
        return sync.hasQueuedThreads();
    }

    @Override
    public boolean tryLock(long timeOut, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(timeOut));
    }

    @Override
    public Condition newCondition(){
       return sync.newCondition();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

}
