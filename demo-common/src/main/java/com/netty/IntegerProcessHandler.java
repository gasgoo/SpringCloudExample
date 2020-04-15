package com.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 读取 整型解码器的输入  业务处理器
 * @Date 2019/12/17 10:28
 * @name IntegerProcessHandler
 */

@Slf4j
public class IntegerProcessHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        Integer integer = (Integer) msg;

        log.info("打印出一个整数:"+integer);
    }
}
