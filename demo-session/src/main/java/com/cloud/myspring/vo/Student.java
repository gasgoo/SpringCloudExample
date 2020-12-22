package com.cloud.myspring.vo;

import com.cloud.anno.MyService;
import lombok.Data;

/**
 * @Desc 通过自定义注解@MyService注解把类注入容器
 * @Date 2020/12/17 14:03
 **/
@Data
@MyService
public class Student {

    private String name;
    private Integer age;

    public void print() {
        System.out.println("name:学生名称,age:20");
    }
}
