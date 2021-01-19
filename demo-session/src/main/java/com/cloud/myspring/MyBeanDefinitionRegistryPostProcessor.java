package com.cloud.myspring;

import com.cloud.anno.MyService;
import com.cloud.myspring.vo.BeanDefBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

/**
 * @Desc BeanDefinition 增删改查
 * @Date 2020/12/17 11:17
 **/
@Component
@Slf4j
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        final String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        log.info(">>>>bean size:{}", beanDefinitionNames.length);
        for (String beanDefName : beanDefinitionNames) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefName);
            //log.info("all Bean>>>>" + beanDefinition);
        }

        //动态注册一个beanDefintion 并设置属性
        GenericBeanDefinition gb = new GenericBeanDefinition();
        gb.setBeanClass(BeanDefBean.class);
        MutablePropertyValues propertyValues = gb.getPropertyValues();
        propertyValues.add("name", "Jack");
        registry.registerBeanDefinition("beanDefinitionBean", gb);
        //扫描某个包路径中包含 MyService注解的所有类
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.addIncludeFilter(new AnnotationTypeFilter(MyService.class));
        scanner.scan("com.cloud");

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) configurableListableBeanFactory;
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        System.out.println("容器启动之前>>>>>>>postProcessBeanFactory");
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        beanFactory.setAllowBeanDefinitionOverriding(true);
        beanFactory.setAllowCircularReferences(true);
        beanFactory.setAllowRawInjectionDespiteWrapping(true);

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
