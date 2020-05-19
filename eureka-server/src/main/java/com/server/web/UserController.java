package com.server.web;

import com.common.model.Constants;
import com.common.utils.JSONUtil;
import com.google.common.base.Strings;
import com.server.annotation.WebLog;
import com.server.domain.UserBean;
import com.server.service.impl.UserService;
import com.server.web.VM.LoginVO;
import com.common.model.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RequestMapping("/user")
@Slf4j
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @WebLog
    @ResponseBody
    @RequestMapping("/queryId")
    public String toIndex(HttpServletRequest request, Model model){
        int userId = Integer.parseInt(request.getParameter("id"));
        UserBean user = this.userService.getUserById(userId);
        return JSONUtil.toJson(user);
    }

    @WebLog
    @ResponseBody
    @RequestMapping("/queryMobile")
    public String selectByMobile(HttpServletRequest request, Model model){
        String mobile =(request.getParameter("mobile"));
        if(Strings.isNullOrEmpty(mobile)){
            return null;
        }
        UserBean user = this.userService.selectByMobile(mobile);
        return JSONUtil.toJson(user);
    }

    @RequestMapping("/allUsers")
    @ResponseBody
    public String getAllUser(){
        System.out.println("=====select DB");
        return  JSONUtil.toJson(userService.getUsers());
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse login(@Valid @RequestBody LoginVO request, HttpServletResponse response) throws NoSuchAlgorithmException {
        //必须要调用validate方法才能实现输入参数的合法性校验
        BaseResponse<String> login = userService.login(request);
        if(Objects.nonNull(login.getData())){
            response.setHeader("token",login.getData());
        }
        return login;
    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<UserBean> add(@RequestBody UserBean userBean,HttpServletRequest request) {
        String token = request.getHeader("token");
        log.info("User:{}", userBean.toString());
        log.info("token:{}",token);
        if (Strings.isNullOrEmpty(token)) {
            return BaseResponse.fail(Constants.FAIL, "用户token必填！");
        } else {
            return userService.add(userBean, token);
        }
    }



    @RequestMapping(value = "/useLists",method = RequestMethod.GET)
    public String updatePage(Model model){
        model.addAttribute("time", new Date());
        model.addAttribute("message", "Young are weclome");

        return "userLists";
    }


}
