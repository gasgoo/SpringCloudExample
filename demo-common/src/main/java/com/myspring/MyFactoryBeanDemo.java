package com.myspring;

import com.common.model.Employee;
import com.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Date 2020/2/24 16:13
 * @name MyFactoryBeanDemo
 */

@Slf4j
public class MyFactoryBeanDemo {

    public static void main(String[] args) throws Exception {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        Car car = (Car) ac.getBean("car");
        System.out.println("新的属性值car:" + car.toString());
        Employee employee = (Employee) ac.getBean("employee");
        System.out.println("emplyee的工号:" + employee.getEmpno());
        BeanLife beanLife = (BeanLife) MyApplicationContextAware.getBean("beanLife");
        System.out.println("自定义获取bean的方式:" + beanLife);


    }
}
