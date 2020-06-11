package com.feign.service;

import com.feign.FeignDemoApplication;
import com.feign.domain.Users;
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
}