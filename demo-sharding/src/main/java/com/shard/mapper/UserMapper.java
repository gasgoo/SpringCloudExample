package com.shard.mapper;

import com.shard.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    Integer insert(UserDO u);

    List<UserDO> findAll();

    List<UserDO> findByUserIds(List<Integer> userIds);
}
