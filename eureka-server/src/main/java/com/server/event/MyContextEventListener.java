package com.server.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 *  容器事件监听器  @EventListener
 * @Date 2020/4/14 16:33
 * @name MyContextEventListener
 */

@Slf4j
@Service
public class MyContextEventListener implements ApplicationListener<MyContextEvent> {

    @Override
    public void onApplicationEvent(MyContextEvent myContextEvent) {
            MyContextEvent event=myContextEvent;
            log.info("MyContextEventListener监听到事件"+ event.getEvent());
    }
}
