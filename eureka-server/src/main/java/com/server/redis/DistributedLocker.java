package com.server.redis;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * Redisson实现分布式锁
 * @Date 2020/7/14 14:22
 * @name DistributedLocker
 */
public interface DistributedLocker {

    RLock lock(String lockKey);

    RLock lock(String lockKey, long timeout);

    RLock lock(String lockKey, TimeUnit unit, long timeout);

    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);

}