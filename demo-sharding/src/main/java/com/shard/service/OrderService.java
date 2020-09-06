package com.shard.service;

import com.shard.domain.Order;
import com.shard.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc
 * @Date 2020/9/6 10:03
 */
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

     public void insert(Order order){
         orderMapper.insert(order);
     }

    public List<Order> findAll(){
        return orderMapper.findAll();
     }
}
