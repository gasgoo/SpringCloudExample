package com.netty.udp.unicast;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 提问请求段的处理器 读取服务端的应答
 * @Date 2020/7/1 14:07
 * @name RequestHandler
 */

@Slf4j
public class RequestHandler extends SimpleChannelInboundHandler<DatagramPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket msg) throws Exception {
        String response=msg.content().toString(CharsetUtil.UTF_8);
        if(response.startsWith(UdpResponseSide.ANSWER)){
            log.info("服务端响应:{}",response);
            channelHandlerContext.close();
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
