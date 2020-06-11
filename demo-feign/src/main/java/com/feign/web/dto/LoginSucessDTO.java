package com.feign.web.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户登录返回的信息
 * @Date 2020/6/11 15:25
 * @name LoginSucessDTO
 */

@Data
public class LoginSucessDTO {

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 角色信息
     */
    private List<String> roles;

    /**
     * 用户名
     */
    private String userName;
}
