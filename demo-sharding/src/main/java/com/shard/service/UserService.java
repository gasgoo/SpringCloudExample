package com.shard.service;

import com.shard.domain.StudentDO;
import com.shard.domain.UserDO;
import com.shard.mapper.StudentMapper;
import com.shard.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc
 * @Date 2020/9/1 20:13
 **/

@Service
@Transactional
public class UserService {

    @Resource
    public UserMapper userMapper;

    @Resource
    public StudentMapper studentMapper;

    public boolean insert(UserDO u) {
        return userMapper.insert(u) > 0 ? true : false;
    }

    public List<UserDO> findAll() {
        return userMapper.findAll();
    }

    public List<UserDO> findByUserIds(List<Integer> ids) {
        return userMapper.findByUserIds(ids);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionTestSucess() {
        UserDO u = new UserDO();
        u.setUserId(13);
        u.setAge(25);
        u.setName("war3 1.27");
        userMapper.insert(u);

        StudentDO student = new StudentDO();
        student.setStudentId(21);
        student.setAge(21);
        student.setName("hehe");
        studentMapper.insert(student);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionTestFailure() throws IllegalAccessException {
        UserDO u = new UserDO();
        u.setUserId(13);
        u.setAge(25);
        u.setName("war3 1.27 good");
        userMapper.insert(u);

        StudentDO student = new StudentDO();
        student = new StudentDO();
        student.setStudentId(21);
        student.setAge(21);
        student.setName("hehe1");
        studentMapper.insert(student);
        throw new IllegalAccessException();
    }

}
