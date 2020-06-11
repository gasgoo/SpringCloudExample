package com.feign.web;

import com.feign.domain.Users;
import com.feign.service.UserService;
import com.feign.web.dto.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户注册接口
 * @Date 2020/6/11 11:34
 * @name AuthController
 */



@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody LoginUser loginUser){
        Users user = new Users();
        user.setUserName(loginUser.getUserName());
        user.setUserPassword(bCryptPasswordEncoder.encode(loginUser.getUserPassword()));
        user.setUserPhone(loginUser.getUserPhone());
        userService.save(user);
        return "success";
    }
}