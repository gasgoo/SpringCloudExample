package com.server.web;

import com.server.annotation.WebLog;
import com.server.domain.UserBean;
import com.server.service.UserService;
import com.server.web.VM.LoginVO;
import com.common.model.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @WebLog
    @RequestMapping("/showUser")
    public UserBean toIndex(HttpServletRequest request, Model model){
        int userId = Integer.parseInt(request.getParameter("id"));
        UserBean user = this.userService.getUserById(userId);
        return user;
    }

    @RequestMapping("/login")
    public BaseResponse login(@Valid @RequestBody LoginVO request, BindingResult result) throws NoSuchAlgorithmException {
        //必须要调用validate方法才能实现输入参数的合法性校验
        return userService.login(request);
    }

    @RequestMapping("/allUsers")
    public List<UserBean> getAllUser(){
        System.out.println("=====select DB");
       return  userService.getUsers();
    }

}
