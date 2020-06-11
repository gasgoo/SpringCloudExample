package com.feign.jwt;

import com.alibaba.fastjson.JSON;
import com.feign.dao.UsersMapper;
import com.feign.domain.Users;
import com.feign.dto.LoginUser;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 重写Security中的userDetail服务
 * @Date 2020/6/11 15:28
 * @name UserDetailsServiceImpl
 */

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String json) throws UsernameNotFoundException {
        LoginUser loginUser = JSON.parseObject(json, LoginUser.class);
        //根据手机号登录
        Users users = usersMapper.selectByMobile(loginUser.getUserPhone());
        if (users == null) {
            log.info("手机号登录失败!");
            return null;
        }
        UserDTO userDTO=new UserDTO();
        userDTO.setRemember(loginUser.getRemember());
        userDTO.setUserName(userDTO.getUsername());
        userDTO.setUserName(json);
        userDTO.setRemember(true);
        List<String> roles= Lists.newArrayList();
        //roles.add(users.getRole());
        userDTO.setRoles(roles);
        return userDTO;

    }
}
