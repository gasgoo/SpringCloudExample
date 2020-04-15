package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * 字符串编码输出
 * @Date 2019/12/17 17:40
 * @name String2ByteEncoder
 */

@Slf4j
public class String2ByteEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        String msg= (String) o;
        byteBuf.writeBytes(msg.getBytes(Charset.forName("UTF-8")));
        log.info("out>>>>{}",msg);
    }
}
