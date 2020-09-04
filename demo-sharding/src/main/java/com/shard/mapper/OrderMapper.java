package com.shard.mapper;

import com.shard.domain.Order;
import com.shard.domain.UserDO;

import java.util.List;

public interface OrderMapper {
    Integer insert(Order u);

    List<Order> findAll();

    List<Order> findByUserIds(List<Integer> orderIds);
}
