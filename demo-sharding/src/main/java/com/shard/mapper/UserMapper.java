package com.shard.mapper;

import com.shard.domain.UserDO;

import java.util.List;

public interface UserMapper {
    Integer insert(UserDO u);

    List<UserDO> findAll();

    List<UserDO> findByUserIds(List<Integer> userIds);
}
