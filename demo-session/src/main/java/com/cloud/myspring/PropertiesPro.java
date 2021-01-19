package com.cloud.myspring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;

import java.util.Properties;

/**
 * @Desc 往环境Environment对象中添加配置  配置中心的原理
 * @Date 2021/1/6 21:06
 **/
public class PropertiesPro implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        StandardEnvironment environment = (StandardEnvironment) beanFactory.getBean(Environment.class);
        Properties properties = new Properties();
        properties.put("app.name", "test-Demo-session");
        PropertiesPropertySource custProperties = new PropertiesPropertySource("custProperties", properties);
        MutablePropertySources mutablePropertySources = environment.getPropertySources();
        mutablePropertySources.addLast(custProperties);


    }
}
