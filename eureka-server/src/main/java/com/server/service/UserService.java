package com.server.service;


import com.server.domain.UserBean;
import com.server.web.VM.LoginVO;
import com.server.web.VM.TokenResponse;
import com.common.model.BaseResponse;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {

    public UserBean getUserById(int userId);

    boolean addUser(UserBean record);

    public BaseResponse<TokenResponse> login(LoginVO request) throws NoSuchAlgorithmException;

    public List<UserBean> getUsers();

    BaseResponse<UserBean> add(UserBean userBean,String token);

    public UserBean selectByMobile(String mobile);


}