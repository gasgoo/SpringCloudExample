package com.cloud.myspring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义获取Bean的方式 ApplicationContextAware 或BeanFactoryAware
 *
 * @Date 2020/2/28 11:49
 * @name MyApplicationContextAware
 */

public class MyApplicationContextAware implements ApplicationContextAware, ImportAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        if (applicationContext == null) {
            throw new NullPointerException("applicationContext is null");
        }
        return applicationContext.getBean(beanName);
    }

    @Override
    public void setImportMetadata(AnnotationMetadata annotationMetadata) {

    }
}
