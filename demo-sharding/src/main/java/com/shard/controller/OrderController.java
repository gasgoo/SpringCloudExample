package com.shard.controller;

import com.shard.domain.Order;
import com.shard.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc
 * @Date 2020/9/3 9:48
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired(required = false)
    private OrderService orderService;


    @RequestMapping("/add")
    public Object add() {
        for (int i = 1; i < 10; i++) {
            Order order = new Order();
            order.setUserId((long) i);
            order.setOrderId((long) i);
            order.setUserName("userName" + i);
            order.setPassWord("passwords" + i);
            orderService.insert(order);
        }
        for (int i = 10; i < 20; i++) {
            Order order = new Order();
            order.setUserId((long) (i + 1));
            order.setOrderId((long) i);
            order.setUserName("userName" + i);
            order.setPassWord("passwords" + i);
            orderService.insert(order);
        }
        return "success";
    }

    @RequestMapping("query")
    private Object queryAll() {
        return orderService.findAll();
    }
}
