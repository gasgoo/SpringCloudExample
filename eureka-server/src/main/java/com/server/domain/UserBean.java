package com.server.domain;

import com.common.model.BaseModel;

import java.io.Serializable;

/**
 * @Date 2019/4/22 10:51
 */
public class UserBean extends BaseModel implements Serializable {

    private Integer id;
    private String mobile;

    private String password;

    private Integer age;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserBean(String mobile, String password, Integer age) {
        this.mobile = mobile;
        this.password = password;
        this.age=age;
    }

    public UserBean() {

    }
}
