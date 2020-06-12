package com.feign.service;

import com.feign.FeignDemoApplication;
import com.feign.dao.RoleMapper;
import com.feign.domain.Users;
import com.feign.dto.UserRolesDTO;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @Date 2020/6/11 14:31
 * @name UserServiceTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignDemoApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void save() {
        Users user=new Users();
        user.setUserName("userName");
        user.setUserPhone("13684889502");
        user.setUserPassword(bCryptPasswordEncoder.encode("123456"));
        user.setUserStatus("0");
        user.setUserId(UUID.randomUUID().toString().replace("-",""));
        Integer save = userService.save(user);
        System.out.println("====id:"+save);
    }

    @Test
    public void testUserRoles(){
        UserRolesDTO userRoles = roleMapper.getUserRoles("62f6cb3ad4c8455ab670eccb2f2c8d4b");
        System.out.println("userRoles:====="+userRoles.toString());
    }
}