package com.server.web;

import com.common.model.Constants;
import com.google.common.base.Strings;
import com.server.annotation.WebLog;
import com.server.domain.UserBean;
import com.server.service.UserService;
import com.server.web.VM.LoginVO;
import com.common.model.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
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

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public BaseResponse login(@Valid @RequestBody LoginVO request, BindingResult result) throws NoSuchAlgorithmException {
        //必须要调用validate方法才能实现输入参数的合法性校验
        return userService.login(request);
    }

    @RequestMapping("/allUsers")
    public List<UserBean> getAllUser(){
        System.out.println("=====select DB");
       return  userService.getUsers();
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<UserBean> add(@RequestBody UserBean userBean,@RequestHeader String token) {
        log.info("User:{}", userBean.toString());
        log.info("token:{}",token);
        if (Strings.isNullOrEmpty(token)) {
            return BaseResponse.fail(Constants.FAIL, "用户token必填！");
        } else {
            return userService.add(userBean, token);
        }
    }

}
