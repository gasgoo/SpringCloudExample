
package com.server.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * @Date 2019/9/26 12:02
 */

@Slf4j
@Service
public class MQConusmer extends AbstractMq {

    private static  DefaultMQPushConsumer consumer=null;

    @PostConstruct
    public void defaultMqPushConsumer(){
        consumer =new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(nameServer);
        try{
            consumer.subscribe(topicName,tag);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener((MessageListenerConcurrently)(list, context) ->{
                try {
                    for (MessageExt msg : list) {
                        log.info("messageExt:{}", msg);
                        String messageBody = new String(msg.getBody());
                        log.info("消费响应：msgId : " + msg.getMsgId() + ",  msgBody : " + messageBody);
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }catch (Exception e){
                    log.error("get message exception:{}",e);
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

            });
            consumer.start();
            log.info("消费者启动成功>>>>>");
        }catch (Exception e){
            log.error("消费异常{}",e);
        }
    }

}

