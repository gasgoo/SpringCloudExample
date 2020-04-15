package com.server.redis;

import com.server.config.UnableToAquireLockException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redisson实现分布式锁 Redlock
 * @Author gg.rao
 * @Date 2019/4/17 19:18
 */
//@Component
public class RedisLocker implements DistributedLocker {

    private final static String LOCKER_PREFIX = "lock:";
    @Autowired
    private RedissonConnector redissonConnector;

    /**
     * 获取锁
     * @param resourceName 锁的名称
     * @param worker       获取锁后的处理类
     * @return 处理完具体的业务逻辑要返回的数据
     * @throws Exception
     */
    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws Exception {
        return lock(resourceName,worker,100);
    }

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws Exception {
        RedissonClient client = redissonConnector.getClient();
        RLock lock = client.getLock(LOCKER_PREFIX + resourceName);
        //等待100秒加锁 到时间自动解锁
        boolean success = lock.tryLock(100, lockTime, TimeUnit.SECONDS);
        if(success){
            try {
                return worker.invokeAfterLockAquire();
            }finally {
                lock.unlock();
            }
        }
        throw new UnableToAquireLockException();
    }
}
