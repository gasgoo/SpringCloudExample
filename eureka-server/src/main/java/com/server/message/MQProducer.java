package com.server.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * 消息生产者
 *
 * @Date 2019/9/26 11:41
 */
@Service
@Slf4j
public class MQProducer extends AbstractMq{


    @Value("${rocketmq.producer.group}")
    private String producerGroup;

    private static  DefaultMQProducer producer=null;
    //@PostContruct是spring框架的注解，在方法上加该注解会在项目启动的时候执行该方法，
    // 也可以理解为在spring容器初始化的时候执行该方法。
    @PostConstruct
    public void defaultMQProducer() {
        try {
            producer = new DefaultMQProducer(producerGroup);
            //一个jvm需要启动多个Producer的时候设置实例名称区分
            producer.setInstanceName("producerTest");
            producer.setNamesrvAddr(nameServer);
            producer.setRetryTimesWhenSendFailed(2);
            producer.start();
            log.info("生产者启动成功>>>>>");
        } catch (Exception e) {
            log.error("发送异常{}",e);
            e.printStackTrace();
        }

    }

    public void send(String topic,String msg){
        //创建消息实例
        try {
        Message message = new Message(topicName, tag, ("send:"+msg).getBytes());
        // message.setDelayTimeLevel(10);
        //log.info("发送延迟消息>>>>>");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info(">>>>>>>发送消息开始>>>>>>>>");
        producer.send(message);
        stopWatch.stop();
        log.info("----------------发送消息完成耗时：" + stopWatch.getTotalTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PreDestroy
    public void shutDownProducer(){
        if(producer!=null){
            producer.shutdown();
        }
    }
}
