package com.myspring;


import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * FactoryBean可以把对象放入容器中 Spring整合管理第三方bean 的利刃
 * @Date 2020/2/24 16:08
 * @name MyFactoryBean
 */

@Component("myFactoryBean")
public class MyFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        Car car=new Car();
        car.setName("baoma");
        car.setPrice(50);
         return car;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }




}
