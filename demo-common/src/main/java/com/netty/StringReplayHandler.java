package com.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * String 分包 解码 使用的处理器
 * @Date 2019/12/17 16:58
 * @name StringReplayHandler
 */

@Slf4j
public class StringReplayHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        String s= (String) msg;
        log.info("String解码输出内容:{}",s);
    }
}
