package com.shard.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName StudentDO
 * @Desc
 * @Date 2020/9/1 20:10
 **/
@Getter
@Setter
public class StudentDO implements Serializable {

    private static final long serialVersionUID = 8920597824668331209L;

    private Integer id;

    private Integer studentId;

    private String name;

    private Integer age;

}
