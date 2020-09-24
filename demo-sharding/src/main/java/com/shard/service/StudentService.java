package com.shard.service;

import com.shard.domain.StudentDO;
import com.shard.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc
 * @Date 2020/9/1 20:13
 **/
@Service
public class StudentService {

    @Resource
    public StudentMapper studentMapper;

    public boolean insert(StudentDO student) {
        return studentMapper.insert(student) > 0 ? true : false;
    }

    public List<StudentDO> findAll() {
        return studentMapper.findAll();
    }

}
