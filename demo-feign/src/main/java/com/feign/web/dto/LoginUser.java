package com.feign.web.dto;

import lombok.Data;

/**
 * 用户登录入参对象
 * @Date 2020/6/11 15:30
 * @name LoginUser
 */

@Data
public class LoginUser {

    /**
     * 是否记住密码
     */
    private Boolean remember;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 用户手机号
     */
    private String userPhone;
}
