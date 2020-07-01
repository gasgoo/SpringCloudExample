package com.netty.udp.broadcast;

import com.netty.udp.LogMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * 日志解码器
 * @Date 2020/7/1 15:39
 * @name LogEventDecoder
 */


public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
        ByteBuf content = datagramPacket.content();
        long sendTime=content.readLong();
        System.out.println("接受到"+sendTime+"发送的消息");
        //获得消息的id
        long msgId = content.readLong();
        //获得分隔符SEPARATOR
        byte sepa = content.readByte();
        //获取读索引的当前位置，就是分隔符的索引+1
        int idx = content.readerIndex();
        //提取日志消息，从读索引开始，到最后为日志的信息
        String sendMsg = content.slice(idx ,
                content.readableBytes()).toString(CharsetUtil.UTF_8);
        //构建一个新的 LogMsg 对象，并且将它添加到（已经解码的消息的）列表中
        LogMsg event = new LogMsg(datagramPacket.sender(),
                msgId, sendMsg);
        //作为本handler的处理结果，交给后面的handler进行处理
        list.add(event);
    }
}
