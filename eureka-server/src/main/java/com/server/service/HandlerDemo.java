package com.server.service;

import com.server.annotation.HandlerType;
import com.server.domain.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 针对 Oders的订单类型用策略模式 替换 N个 if 处理的实例
 * @Date 2019/9/26 9:54
 */
@Service
@Slf4j
public class HandlerDemo {

    @Autowired
    private List<OrderService> orderServiceList;


    public void handle(Orders order){
        Optional<OrderService> first =Optional.empty();
        for(OrderService orderService : orderServiceList){
            HandlerType annotation = AnnotationUtils.findAnnotation(orderService.getClass(), HandlerType.class);
            if(Objects.nonNull(annotation)&& annotation.value().equals(order.getType())){
                first=Optional.of(orderService);
            }
        }
        if(first.isPresent()){
            log.info("获取到处理器{}",first.get());
            OrderService orderService = first.get();
            orderService.handle(order);
        }
        log.info(">>>>>>>end");
    }

    @Async
    public void asyncHandle(Orders orders){
        log.info("异步处理线程{}>>>>>start",Thread.currentThread().getName());
        this.handle(orders);
    }



}
