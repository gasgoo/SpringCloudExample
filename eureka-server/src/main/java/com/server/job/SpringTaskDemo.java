package com.server.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Scheduled 注解开启定时任务
 * @Date 2019/11/4 9:47
 * @name SpringTaskDemo
 */


@Component
public class SpringTaskDemo {

    private static int count;

    //间隔1000秒执行
    @Scheduled(fixedRate = 1000000)
    public void job(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println(Instant.now());
        System.out.println("Spring task execute times:"+count++);
    }
}
