package com.feign.jwt;

import com.alibaba.fastjson.JSON;
import com.feign.dao.RoleMapper;
import com.feign.dao.UsersMapper;
import com.feign.domain.Users;
import com.feign.dto.LoginUser;
import com.feign.dto.UserRolesDTO;
import com.google.common.collect.Lists;
import com.netflix.discovery.converters.Auto;
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

    @Autowired
    private RoleMapper roleMapper;

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
        userDTO.setUserName(users.getUserName());
        userDTO.setUserPassword(users.getUserPassword());
        userDTO.setUserId(users.getUserId());
        userDTO.setUserPhone(users.getUserPhone());
        userDTO.setRoles(getUserRoles(users.getUserId()).getRoleNames());
        return userDTO;

    }

    public UserRolesDTO getUserRoles(String userId){
        return roleMapper.getUserRoles(userId);
    }
}
