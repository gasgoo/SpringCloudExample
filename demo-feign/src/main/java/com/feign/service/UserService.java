package com.feign.service;

import com.feign.dao.UsersMapper;
import com.feign.domain.Users;
import com.feign.jwt.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Date 2020/6/11 10:16
 * @name UserService
 */



@Service
@Slf4j
public class UserService {

    @Autowired
    private UsersMapper userMapper;

    public Integer save(Users user) {
       return userMapper.insert(user);
    }

    public Users queryUserByMobile(String mobile){
        Users users = userMapper.selectByMobile(mobile);
        if(Objects.isNull(users)){
            log.info("手机号无效!{}",mobile);
            return null;
        }
        return users;
    }

}
