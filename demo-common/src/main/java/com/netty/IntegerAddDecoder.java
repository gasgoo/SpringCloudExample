package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 整数相加解码器  分包 多步骤解析
 * @Date 2019/12/17 11:30
 * @name IntegerAddDecoder
 */


public class IntegerAddDecoder extends ReplayingDecoder<IntegerAddDecoder.Status> {


    private int first;

    private int second;

    public IntegerAddDecoder(){
        super(Status.P1);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (state()) {
            case P1:
                first = byteBuf.readInt();
                //第一步解析成功 进入第二部
                checkpoint(Status.P2);
                System.out.println("第一步>>>"+first);
                break;
            case P2:
                second = byteBuf.readInt();
                Integer sum = first + second;
                System.out.println(first+" + "+second+"=>>>>sun:"+sum);
                checkpoint(Status.P1);
                break;
            default:
                break;
        }
    }

    enum Status{
        P1,P2
    }



}
