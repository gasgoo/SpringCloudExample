package com.cloud.myspring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Desc
 * @Date 2020/12/28 20:58
 **/

@Component
public class SmartInstantiationAwareBeanPostProcessorDemo implements SmartInstantiationAwareBeanPostProcessor {

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        System.out.println(">>>>>> 自定义getEarlyBeanReference" + beanName);
        return bean;
    }
}
