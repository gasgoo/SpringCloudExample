package com.netty.udp.unicast;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * 应答类Handler
 * @Date 2020/7/1 14:19
 * @name ResponseHandler
 */

@Slf4j
public class ResponseHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final String[] DICTIONARY = {
            "只要功夫深，铁棒磨成针。",
            "旧时王谢堂前燕,飞入寻常百姓家。",
            "洛阳亲友如相问，一片冰心在玉壶。",
            "一寸光阴一寸金，寸金难买寸光阴。",
            "老骥伏枥，志在千里，烈士暮年，壮心不已" };

    private static Random r = new Random();
    private String nextQuote(){
        return DICTIONARY[r.nextInt(DICTIONARY.length-1)];
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket msg) throws Exception {
        //拿到请求
        String req = msg.content().toString(CharsetUtil.UTF_8);
        if(UdpRequestSide.QUESTION.equals(req)){
            String answer=UdpResponseSide.ANSWER+nextQuote();
            log.info("请求内容:{}",req);
            channelHandlerContext.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(answer,CharsetUtil.UTF_8),msg.sender()));
        }

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
