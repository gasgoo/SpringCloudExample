package com.server.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring事件发布
 * @Date 2020/4/14 16:36
 * @name MyContextEventClient
 */
@Slf4j
@Component
public class MyContextEventPublisher implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public void happenEvent(){
      log.info("MyContextEventClient事件发生了>>>>>.");
       MyContextEvent event =new MyContextEvent(applicationContext);
       //事件发布
       applicationContext.publishEvent(event);
    }


}
