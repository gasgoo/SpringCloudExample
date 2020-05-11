package com.server.service;


import com.server.domain.Orders;

/**
 * 订单服务
 *
 * @Date 2019/9/26 9:42
 */
public abstract class OrderService {

    /**
     * @return
     * @Description 根据订单类型不同做不同处理
     * @Date 2019/9/26 9:43
     * @Param
     **/
    public abstract String handle(Orders order);



}


