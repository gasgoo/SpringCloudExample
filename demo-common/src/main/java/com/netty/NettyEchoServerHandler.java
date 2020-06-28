package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * 表示当前Handler可以共享的注解
 * @ChannelHandler.Sharable
 * @Date 2019/12/12 19:22
 * @name NettyEchoHandler
 */

@Slf4j
@ChannelHandler.Sharable
public class NettyEchoServerHandler extends ChannelInboundHandlerAdapter {


    public static  final NettyEchoServerHandler INSTANCE =new NettyEchoServerHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf buf = (ByteBuf) msg;
        log.info("msg type:"+ (buf.hasArray()? "堆内存":"直接内存"));
        int len = buf.readableBytes();
        byte[] arr =new byte[len];
        buf.getBytes(0,arr);
        log.info("server received:{}",new String(arr, Charset.forName("UTF-8")));

        log.info("写回前: msg.refCnt: {}", ((ByteBuf) msg).refCnt());

        //返回给客户端的数据
        String result ="Hello Client";
        ByteBuf byteBuf= Unpooled.buffer();
        byte[] bytes =result.getBytes(Charset.forName("UTF-8"));
        byteBuf.writeBytes(bytes );
        ctx.channel().writeAndFlush(byteBuf);

        System.out.println("=========");

    }

   /* @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }*/
}
