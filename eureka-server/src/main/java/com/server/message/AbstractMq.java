package com.server.message;

import org.springframework.beans.factory.annotation.Value;

/**
 * @Date 2019/9/26 14:25
 */
public abstract  class AbstractMq {

    protected static final String consumerGroup = "CONSUMERGROUP";
    protected static final String topicName = "Topic-test";
    protected static final String tag = "pushTag";

    @Value("${rocketmq.nameServer}")
    protected String nameServer;

}
