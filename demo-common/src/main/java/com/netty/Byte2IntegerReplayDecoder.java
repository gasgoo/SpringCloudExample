package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * com.netty.Byte2IntegerReplayDecoder 的升级版
 * @Date 2019/12/17 10:39
 * @name Byte2IntegerReplayDecoder
 */

@Slf4j
public class Byte2IntegerReplayDecoder extends ReplayingDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
             int i =byteBuf.readInt();
             log.info("解码出一个整数: "+i);
             list.add(i);
    }





}
