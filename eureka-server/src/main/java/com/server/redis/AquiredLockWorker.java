package com.server.redis;

/**
 * 获取锁后需要处理的逻辑
 * @Author gg.rao
 * @Date 2019/4/17 19:11
 */
public interface AquiredLockWorker<T> {

    T invokeAfterLockAquire() throws Exception;
}
