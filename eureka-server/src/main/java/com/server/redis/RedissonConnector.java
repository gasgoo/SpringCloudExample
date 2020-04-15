package com.server.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

import javax.annotation.PostConstruct;

/**
 * 获取RedissonClient连接类
 * @Author gg.rao
 * @Date 2019/4/17 19:14
 */
//@Component
public class RedissonConnector {
    private RedissonClient redisson;
    @PostConstruct
    public void init(){
        redisson = Redisson.create();
    }

    public RedissonClient getClient(){
        return redisson;
    }
}
