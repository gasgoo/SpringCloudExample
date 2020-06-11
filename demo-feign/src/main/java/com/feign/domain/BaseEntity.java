package com.feign.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Date 2020/6/11 10:21
 * @name BaseEntity
 */


public abstract class BaseEntity {

    private Integer id;

    private Date createDate;

    private Date updateDate;

    public Integer getId() {
        return id;
    }
}
