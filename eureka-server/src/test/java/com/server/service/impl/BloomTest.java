package com.server.service.impl;

import com.server.EurekaDemoApplication;
import com.server.bloom.BloomDemo;
import com.server.bloom.HashUtils;
import com.server.bloom.RedisBitMap;
import com.server.domain.Orders;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

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
        List<Orders> redisOrderList= Lists.newArrayList();
        buildList(redisOrderList);
        Map map = new HashMap<String,String>();
        map.put("hashKey1",redisOrderList);
        map.put("hashKey2","value2");
        map.put("hashKey3","value3");
        HashOperations<String,String,String> ops = redisTemplate.opsForHash();
        ops.putAll("key2",map);
        map = ops.entries("key2");
        Iterator<String> iter = map.keySet().iterator();
        while(iter.hasNext()){
            System.out.println(map.get(iter.next()));
        }

    }

    @Test
    public void listTest(){
        List<Orders> redisOrderList= Lists.newArrayList();
        buildList(redisOrderList);

        ListOperations<String, List<Orders>> ops = redisTemplate.opsForList();
        Long size = ops.leftPush("listKeyOrder", redisOrderList);
        System.out.println(">>>>"+size);
        List<Orders> orders = ops.leftPop("listKeyOrder");
        Iterator<Orders> itr = orders.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }

    private void buildList(List<Orders> redisOrderList) {
        for(int i=0;i<50;i++){
            Orders orders=new Orders();
            orders.setCode("code"+i);
            orders.setPrice(new BigDecimal("50"));
            orders.setType("1");
            redisOrderList.add(orders);
        }
    }
}
