package com.zk;

/**
 * @Date 2020/7/15 11:37
 * @name ExLock
 */
public interface ExLock {

    //ExtLock基于zk实现分布式锁
    public void lock();

    //释放锁
    public void unLock();
}
