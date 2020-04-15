package com.mq;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 模拟 消息队列的 Broker
 * @Date 2019/9/16 16:34
 */
public class Broker {

    // 对应 RocketMQ 中 MessageQueue，默认情况下 1 个 Topic 包含 4 个 MessageQueue
    private LinkedBlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    //消息发送  实际发送消息到Broker服务器使用netty发送
    public void sendMsg(String msg){
        try {
            messageQueue.put(msg);
            //同步或异步持久化
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //获取消息
    public String getMsg(){
        try {
            return  messageQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAllMsg(){
        StringBuilder sb=new StringBuilder();
        messageQueue.iterator().forEachRemaining(msg ->{
            sb.append(msg+"\n");
        });
        return sb.toString();
    }

}
