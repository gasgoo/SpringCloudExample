package com.server.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Date 2019/4/22 10:51
 */
@Setter
@Getter
@ToString
@Data
public class UserBean  implements Serializable {

    private Integer id;

    private Long uid;

    private String userName;
    private String mobile;

    private String password;

    private Integer age;

    private String address;

    private Date createDate;

    private Date updateDate;


    public UserBean(String mobile, String password,int age) {
        this.mobile = mobile;
        this.password = password;
        this.age=age;
    }

    public UserBean() {
    }
}
