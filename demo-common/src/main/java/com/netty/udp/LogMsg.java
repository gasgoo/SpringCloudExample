package com.netty.udp;

import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;

/**
 * 类说明：日志实体类
 */
@Getter
@Setter
public  final class LogMsg {
    public static final byte SEPARATOR = (byte) ':';
    /*源的 InetSocketAddress*/
    private final InetSocketAddress source;
    /*消息内容*/
    private final String msg;
    /*消息id*/
    private final long msgId;
    /*消息发送的时间*/
    private final long time;

    //用于传入消息的构造函数
    public LogMsg(String msg) {
        this(null, msg,-1,System.currentTimeMillis());
    }

    //用于传出消息的构造函数
    public LogMsg(InetSocketAddress source, long msgId,
                  String msg) {
        this(source,msg,msgId,System.currentTimeMillis());
    }

    public LogMsg(InetSocketAddress source, String msg, long msgId, long time) {
        this.source = source;
        this.msg = msg;
        this.msgId = msgId;
        this.time = time;
    }


}
