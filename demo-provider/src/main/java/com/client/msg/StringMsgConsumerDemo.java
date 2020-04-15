package com.client.msg;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * mq消费实例
 * @Date 2019/9/26 15:28
 */
@Service
@RocketMQMessageListener(topic = "Topic-test",selectorExpression = "pushTag",consumerGroup ="demo-provider-CONSUMERGROUP")
@Slf4j
public class StringMsgConsumerDemo implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {


    @Value("${rocketmq.nameServer}")
    protected String nameServer;

    @Override
    public void onMessage(String s) {
        log.info("接收消息内容:{}", Objects.toString(s));
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        defaultMQPushConsumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
    }
}
