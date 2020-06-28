package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * netty 客户端处理器
 * @Date 2019/12/13 9:10
 * @name NettyEchoClientHandler
 */

@ChannelHandler.Sharable
public class NettyEchoClientHandler extends ChannelInboundHandlerAdapter  {


    public static final NettyEchoClientHandler INSTANCE =new NettyEchoClientHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
        ByteBuf  buf = (ByteBuf) o;
        int len= buf.readableBytes();

        byte[] arr = new byte[len];
        System.out.println("客户端接收的内容>>>"+new String(arr,"UTF-8"));


        System.out.println("=========");

        buf.release();

        //向后传递 自动释放
        //super.channelRead(ctx,o);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello Netty", CharsetUtil.UTF_8));
    }
}
