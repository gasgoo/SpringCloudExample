package com.myspring;

import com.common.model.Employee;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * Spring容器后置处理器的使用 修改容器中对象的属性 BeanDefinition创建后做些事情
 * @Date 2020/2/27 15:38
 * @name MyBeanFactoryPostprocessor
 */

public class MyBeanFactoryPostprocessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //简单理解spring当中的BeanDefinition就是java当中的Class
        //描述springbean当中的scope、lazy，以及属性和方法等等其他信息
        BeanDefinition carBean = beanFactory.getBeanDefinition("car");
        System.out.println("MyBeanFactoryPostprocessor修改car的属性>>>"+carBean.getBeanClassName());
        carBean.getPropertyValues().add("name","BgValue");
        carBean.setScope("prototype");
       //动态注册一个beanDefintion  没有扫描到的class  Employee
        DefaultListableBeanFactory df= (DefaultListableBeanFactory) beanFactory;
        GenericBeanDefinition gb=new GenericBeanDefinition();
        gb.setBeanClass(Employee.class);
        df.registerBeanDefinition("employee",gb);
        gb.getPropertyValues().add("empno",10086L);

    }
}
