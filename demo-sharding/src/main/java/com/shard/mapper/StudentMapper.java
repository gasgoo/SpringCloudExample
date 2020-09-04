package com.shard.mapper;

import com.shard.domain.StudentDO;

import java.util.List;

public interface StudentMapper {

    Integer insert(StudentDO s);

    List<StudentDO> findAll();

    List<StudentDO> findByStudentIds(List<Integer> studentIds);
}
