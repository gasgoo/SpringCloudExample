package com.mq;

/**
 * 消息队列重的生产者
 * @Date 2019/9/16 16:42
 */
public class Producer {

    private Broker broker;

    public void  connectBroker(Broker broker){
        this.broker=broker;
    }

    public void asyncSendMsg(String msg){
        if(broker==null){
            throw new RuntimeException("请连接broker!");
        }
        new Thread(() ->{
            broker.sendMsg(msg);
        }).start();
    }


}
