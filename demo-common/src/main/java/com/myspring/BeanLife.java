package com.myspring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * bean生命周期
 * @Date 2020/2/27 10:13
 * @name BeanLife
 */

public class BeanLife implements ApplicationContextAware {

    //注入car属性
    @Autowired
    private  Car car;


    public BeanLife(){
        System.out.println(">>>>构造方法");
    }

    @PostConstruct
    public void finit(){
        System.out.println(">>>>生命周期初始化回调PostConstruct");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(">>>>属性注入成功"+car.toString());
        System.out.println(">>>>回调Aware接口");
    }

    public void test(){

        System.out.println("BeanLife bean ");
    }


}
