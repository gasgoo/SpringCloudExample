package com.server.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *  SpringBoot 监听方式mq消费  selectorExpression 属性用于选择tag
 * @Date 2019/12/23 17:11
 * @name MQRocketConsumer
 */

@Slf4j
@Service
@RocketMQMessageListener(topic = "Topic-test", selectorExpression ="pushTag2",consumerGroup = "Topic_test-topic-test")
public class MQRocketListenerConsumer implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {


    @Override
    public void onMessage(String msg) {
        log.info("消费者收到的消息:{}",msg);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
        defaultMQPushConsumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
        defaultMQPushConsumer.setAllocateMessageQueueStrategy(new AllocateMessageQueueAveragely());
        defaultMQPushConsumer.setMaxReconsumeTimes(3);
    }
}
