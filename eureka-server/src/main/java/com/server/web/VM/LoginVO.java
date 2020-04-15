package com.server.web.VM;

import javax.validation.constraints.NotEmpty;

/**
 * 登录请求对象
 * @Date 2019/4/22 10:53
 */
public class LoginVO {

    @NotEmpty
    private String mobile;

    @NotEmpty
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
