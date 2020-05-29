package com.server.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 *  Spring事件类型 ApplicationContextEvent=容器事件  requestHandlerEvent web请求处理事件
 *  事件源---事件广播器----事件监听注册表
 * @Date 2020/4/14 16:26
 * @name MyContextEvent  自定义的容器事件
 */

@Slf4j
@Component
public class MyContextEvent extends ApplicationEvent {
    //事件发生
    private String event;

    public MyContextEvent(ApplicationContext source) {
        super(source);
    }


    public String getEvent(){
        log.info("自定义容器事件MyContextEvent>>>Happen");
        return  this.event;
    }
}
