package com.cloud.myspring;

import com.cloud.myspring.vo.Car;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * BeanPostProcessor 后置处理器 bean的创建前后做些事情
 * @Date 2020/2/28 11:22
 * @name MyBeanPostProcessor
 */


public class MyBeanPostProcessor  implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //System.out.println("bean后主处理上>>>>start");
        if(beanName.equals("car")){
            Car car= (Car) bean;
            System.out.println("car修改之前的车:"+car.toString());
            System.out.println("update car bean");
            car.setPrice(88888);
            car.setName("哈佛");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //System.out.println("bean后主处理上>>>>end"+beanName);

        return bean;
    }
}
