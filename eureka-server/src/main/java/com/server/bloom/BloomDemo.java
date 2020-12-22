package com.server.bloom;

import com.alibaba.fastjson.JSON;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.server.domain.Orders;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.redisson.misc.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Desc
 * @Date 2020/10/18 9:55
 */
@Slf4j
@Service
public class BloomDemo {

    @Autowired
    private RedisTemplate redisTemplate;

    private static int size = 100;//预计要插入多少数据

    private static double fpp = 0.01;//期望的误判率

    private static BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), size, fpp);
    //private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

    public void process() {
        List<Orders> ordersList=Lists.newArrayList();
        //插入数据1w个订单
        for (int i = 0; i <=size; i++) {
            Orders orders=new Orders();
            orders.setCode("order"+i);
            ordersList.add(orders);
            bloomFilter.put(orders.getCode());
        }
        int count = 0;
        List<String> successList= Lists.newArrayList();
        for (int i = 90; i <= 100; i++) {
            String key = "order" + i;
            if (!bloomFilter.mightContain(key)) {
                successList.add(key);
            }
        }
        System.out.println("非重复的数据："+successList.size());
        System.out.println("非重复的数据:"+ JSON.toJSONString(successList));
        //非重复的数据放入缓存  后续增量更新
        ValueOperations<String,String> ops=redisTemplate.opsForValue();
        for(int i=0;i<successList.size();i++) {
            Boolean orderList = ops.setBit("orderList", HashUtils.hash(successList.get(i)), true);
            System.out.println(orderList+">>>"+successList.get(i)+"的偏移量offset="+ HashUtils.hash(successList.get(i)));
        }
        ops.setBit("orderList",HashUtils.hash("addOrderCode44"),true);
        log.info(">>>>>end");

    }

}