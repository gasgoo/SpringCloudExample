package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 字符串分包解码器
 *
 * @Date 2019/12/17 16:48
 * @name StringReplayDecoder
 */

@Slf4j
public class StringReplayDecoder extends ReplayingDecoder<StringReplayDecoder.Status> {

    private int length;

    private byte[] inBytes;

    public StringReplayDecoder() {
        super(Status.P1);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (state()) {
            //第一步从 byteBuf种读取长度
            case P1:
                length = byteBuf.readInt();
                log.info("字符串分包读取长度:{}",length);
                inBytes = new byte[length];
                checkpoint(Status.P2);
                break;
            case P2:
                //读取内容
                byteBuf.readBytes(inBytes, 0, length);
                String content=new String(inBytes, "UTF-8");
                list.add(content);
                log.info("读取内容content:{}",content);
                checkpoint(Status.P1);
                break;
            default:
                break;

        }
    }

    enum Status {
        P1, P2
    }
}
