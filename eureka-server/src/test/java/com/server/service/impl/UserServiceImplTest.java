package com.server.service.impl;

import com.server.redis.RedisUtils;
import com.server.EurekaDemoApplication;
import com.server.domain.UserBean;
import com.server.web.VM.LoginVO;
import com.server.web.VM.TokenResponse;
import com.common.model.BaseResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Date 2019/4/22 11:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EurekaDemoApplication.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void getUserById() {
        UserBean userById = userService.getUserById(1);
        System.out.println(userById.getMobile());

    }

    @Test
    @Transactional
    public void addUser() {
        UserBean userBean=new UserBean();
        userBean.setMobile("13817569208");
        userBean.setPassword("testSpringBoot");
        userBean.setUserName("username");
        boolean b = userService.addUser(userBean);
        System.out.println("=="+b +"    id:"+userBean.getId());
    }

    @Test
    public void login() throws NoSuchAlgorithmException {
        LoginVO vo=new LoginVO();
        vo.setMobile("13817569208");
        vo.setPassword("123456");
        BaseResponse<String> login = userService.login(vo);
        System.out.println(login.getData()+"   "+login.getCode()+login.getMsg());
    }
    @Test
    public void testGetAll(){
        List<UserBean> users = userService.getUsers();
        System.out.println("用户数:"+users.size());
    }

}