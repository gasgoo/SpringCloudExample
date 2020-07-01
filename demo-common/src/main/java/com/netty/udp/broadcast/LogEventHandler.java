package com.netty.udp.broadcast;

import com.netty.udp.LogMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 解码，将DatagramPacket解码为实际的日志实体类
 * @Date 2020/7/1 16:04
 * @name LogEventHandler
 */

@Slf4j
public class LogEventHandler extends SimpleChannelInboundHandler<LogMsg> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogMsg logMsg) throws Exception {
        StringBuilder builder=new StringBuilder();
        builder.append(logMsg.getTime());
        builder.append(" [");
        builder.append(logMsg.getSource().toString());
        builder.append("] ：[");
        builder.append(logMsg.getMsgId());
        builder.append("] ：");
        builder.append(logMsg.getMsg());
        //打印 LogMsg 的数据
        log.info(builder.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
        //当异常发生时，打印栈跟踪信息，并关闭对应的 Channel
        cause.printStackTrace();
        ctx.close();
    }
}
