package com.gateway.service;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @Date 2019/12/18 13:56
 * @name InitDemo
 */

@Slf4j
@Service
public class InitDemo {


    private static final Map<String,String> startMap = Maps.newConcurrentMap();

    private static boolean loaded=false;

    /**
     * @Description 初始化共享数据
     * @Date 2019/12/18 14:07
     **/
    @PostConstruct
    public void init(){
        if(loaded){
            return;
        }
        synchronized (this){
            if(loaded){
                return;
            }
            log.info("start check over start init");
            //从数据库种读取数据放到startMap中
            loaded =true;
            log.info("init end");

        }
    }
}
