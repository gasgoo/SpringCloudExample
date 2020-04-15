package com.server.redis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 *
 * @Author gg.rao
 * @Date 2019/5/15 11:32
 */
@Component
public  class RedisUtils {

    private Logger log = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    private RedisTemplate redisTemplate;


    /*获取缓存值*/
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /*值放入缓存*/
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /*放入缓存并设置有效期*/
    public boolean set(String key, Object value,long time) {
        try {
            redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return boolean
     * @Author gg.rao
     * @Description 指定缓存失效时间
     * @Date 2019/5/15 11:36
     * @Param [key, time]
     **/
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("{}", e);
            return false;
        }
    }

    /*获取过期时间*/
    public long getExpireTime(String key) {
        return redisTemplate.getExpire(key);
    }

    /*判断key是否存在*/
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /*删除缓存*/
    public boolean del(String... key) {
        boolean flag=false;
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                flag = redisTemplate.delete(key[0]);
            } else {
               long count= redisTemplate.delete(CollectionUtils.arrayToList(key));
               if(count>0){
                   flag=true;
               }else{
                   flag=false;
               }
            }
        }
        return flag;
    }

    /*递增*/
    public long increment(String key,long delta){
        if(delta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key,delta);
    }

    /**
     * @Author gg.rao
     * @Description 获取hashKey对应的所有键值
     * @Date 2019/5/16 19:37
     * @Param [key]
     * @return java.utils.Map<java.lang.Object,java.lang.Object>
     **/
    public Map<Object,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /*获取hashKey对应的某个值*/
    public Object hget(String key,String item) {
        return redisTemplate.opsForHash().get(key,item);
    }
    /*hash键值对存入缓存*/
    public boolean hmset(String key,Map<String,Object> map){
        try{
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean hmset(String key,Map<String,Object> map,long time){
        try{
            redisTemplate.opsForHash().putAll(key,map);
            if(time>0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /*向一张hash表中存入数据，不存在则创建*/
    public boolean hset(String key,String item,Object value,long time){
        try{
            redisTemplate.opsForHash().put(key,item,value);
            if(time>0){
                expire(key,time);
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    /*删除hash表中的键值*/
    public void hdel(String key,Object... item){
        redisTemplate.opsForHash().delete(key,item);
    }

    /*判断hash表中是否有该项的值*/
    public boolean hasKey(String key,String item){
        return redisTemplate.opsForHash().hasKey(key,item);
    }
}
