package com.server.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 *  容器事件监听器  @EventListener
 * @Date 2020/4/14 16:33
 * @name MyContextEventListener
 */

@Slf4j
@Service
public class MyContextEventListener implements ApplicationListener {


    @Override
    @ConditionalOnBean
    @ConditionalOnClass
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof MyContextEvent){
            MyContextEvent event= (MyContextEvent) applicationEvent;
            log.info("自定义事件监听MyContextEvent:"+event.getEvent());

        }else{
            log.debug("其他事件:"+ applicationEvent);
        }

    }
}
