package com.server.service.impl;

import com.server.EurekaDemoApplication;
import com.server.bloom.BloomDemo;
import com.server.bloom.HashUtils;
import com.server.bloom.RedisBitMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Desc
 * @Date 2020/10/18 10:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EurekaDemoApplication.class)
public class BloomTest {

    @Autowired
    private RedisBitMap redisBitMap;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BloomDemo bloomDemo;


    @Test
    public void testInsert(){
        redisBitMap.batchImport();
    }

    @Test
    public void testBit(){
        String key="bitTestKey";
        ValueOperations<String,Object> ops = redisTemplate.opsForValue();
        Boolean bitTestKey = ops.setBit(key, HashUtils.hash(key), true);
        System.out.println(HashUtils.hash(key)+"bitTestKey 赋值结果:"+bitTestKey);
        if(!bitTestKey){
            Boolean bit = ops.getBit(key, 200);
            System.out.println(">>>>"+bit);
        }else{
            System.out.println("赋值失败");
        }

    }

    @Test
    public void testBloom(){
        bloomDemo.process();
    }

    @Test
    public void testHash(){

    }
}
