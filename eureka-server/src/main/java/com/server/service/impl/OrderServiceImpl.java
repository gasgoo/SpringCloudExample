package com.server.service.impl;

import com.server.annotation.HandlerType;
import com.server.domain.Orders;
import com.server.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Date 2019/9/26 9:45
 */
@HandlerType(value = "1")
@Service
@Slf4j
public class OrderServiceImpl extends OrderService {
    /**
     * @param order
     * @return
     * @Description 根据订单类型不同做不同处理
     * @Date 2019/9/26 9:43
     * @Param
     */
    @Override
    public String handle(Orders order) {
        log.info(">>>>订单类型1处理中");
        return "订单类型1处理中。。。。。";
    }
}
