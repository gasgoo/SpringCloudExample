package com.server.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 消息接收者
 * @Date 2019/6/21 10:00
 */
public class Receiver {
    private static final Logger log= LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch countDownLatch;
    public Receiver(CountDownLatch latch){
        this.countDownLatch=latch;
    }

    public void receiveMessage(String msg){
        log.info("接收消息内容:{}",msg);
        countDownLatch.countDown();
    }
}
