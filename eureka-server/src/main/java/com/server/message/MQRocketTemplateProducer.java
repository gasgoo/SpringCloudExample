package com.server.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Spring Boot消息发送方式
 * @Date 2019/9/26 17:16
 */
@Service
@Slf4j
public class MQRocketTemplateProducer  {

    private  final String msg_topic="Topic-test:pushTag2";
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void sendMsg(String msg){
        log.info(">>>>>>>>>>start send message");
        rocketMQTemplate.convertAndSend(msg_topic,msg);
    }


}
