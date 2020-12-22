package com.cloud.config;

import com.cloud.myspring.*;
import com.cloud.myspring.vo.BeanLife;
import com.cloud.myspring.vo.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 2020/2/24 16:26
 * @name AppConfig
 */

//@ComponentScan() 无法扫描自己所有需要手动注册AppConfig到容器中
@Configuration
public class AppConfig {
    @Bean
    public MyFactoryBean myFactoryBean(){
        return new MyFactoryBean();
    }

    @Bean
    public Car car(){
        Car car=new Car();
        car.setPrice(1000);
        car.setName("carSetter");
        return new Car();
    }

    @Bean
    public BeanLife beanLife(){
        return new BeanLife();
    }

    //@Bean
    public MyBeanFactoryPostprocessor beanFactoryPostprocessor(){
        return  new MyBeanFactoryPostprocessor();
    }

    //@Bean
    public MyBeanPostProcessor beanPostProcessor(){
        return new MyBeanPostProcessor();
    }

    //@Bean
    public MyApplicationContextAware applicationContextAware(){
        return new MyApplicationContextAware();
    }
}
