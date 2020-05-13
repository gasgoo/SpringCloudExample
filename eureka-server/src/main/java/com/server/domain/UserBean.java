package com.server.domain;

import com.common.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Date 2019/4/22 10:51
 */
@Setter
@Getter
@ToString
public class UserBean extends BaseModel implements Serializable {

    private Integer id;
    private String mobile;

    private String password;

    private Integer age;

    private String address;


    public UserBean(String mobile, String password,int age) {
        this.mobile = mobile;
        this.password = password;
        this.age=age;
    }

    public UserBean() {
    }
}
