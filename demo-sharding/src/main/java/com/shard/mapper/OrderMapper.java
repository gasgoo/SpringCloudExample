package com.shard.mapper;

import com.shard.domain.Order;
import com.shard.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    Integer insert(Order u);

    List<Order> findAll();

    List<Order> findByUserIds(List<Integer> userIds);
}
