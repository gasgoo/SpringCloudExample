package com.netty.httpServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

/**
 * @Date 2020/6/29 20:21
 * @name HttpClientInboundHandler
 */


public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpResponse httpResponse = (FullHttpResponse)msg;
        System.out.println(httpResponse.headers());
        ByteBuf buf = httpResponse.content();
        System.out.println(buf.toString(CharsetUtil.UTF_8));
        httpResponse.release();
    }
}
