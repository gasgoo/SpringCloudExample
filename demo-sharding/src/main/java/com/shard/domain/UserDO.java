package com.shard.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName UserDO
 * @Desc
 * @Date 2020/9/1 20:09
 **/
@Getter
@Setter
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userId;

    private String name;

    private Integer age;

}
