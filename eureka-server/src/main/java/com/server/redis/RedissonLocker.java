package com.server.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redisson实现分布式锁
 *
 * @Date 2020/7/14 14:21
 * @name RedissonLock
 */

@Component
public class RedissonLocker implements DistributedLocker {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * @Description 阻塞加锁  拿不到lock就不罢休，不然线程就一直block
     * @Date 2020/7/14 14:24
     **/
    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        System.out.println(lockKey + "加锁成功!");
        return lock;
    }

    /**
     * @Description leaseTime为加锁时间，单位为秒
     * @Date 2020/7/14 14:24
     **/
    @Override
    public RLock lock(String lockKey, long timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return null;
    }

    @Override
    public RLock lock(String lockKey, TimeUnit unit, long timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }
}
