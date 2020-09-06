package com.shard.mapper;

import com.shard.domain.StudentDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {

    Integer insert(StudentDO s);

    List<StudentDO> findAll();

    List<StudentDO> findByStudentIds(List<Integer> studentIds);
}
