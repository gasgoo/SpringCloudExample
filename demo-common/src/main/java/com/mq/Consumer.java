package com.mq;

/**
 * 消息消费者
 * @Date 2019/9/16 16:47
 */
public class Consumer {

    private Broker broker;

    public void connectBroker(Broker broker){
        this.broker=broker;
    }

    public String syncPullMsg(){
        return broker.getMsg();
    }
}
