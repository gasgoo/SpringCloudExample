package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 整数解码器 例子  netty 解码器是把ByteBuf解码成 java pojo  然后放入List<Object>传入下一个处理器
 * @Date 2019/12/17 10:20
 * @name Byte2IntegerDecoder
 */

@Slf4j
public class Byte2IntegerDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        while(byteBuf.readableBytes()>=4){
            int i=byteBuf.readInt();
            log.info("解码出整数: "+i);
            list.add(i);
        }
    }
}
