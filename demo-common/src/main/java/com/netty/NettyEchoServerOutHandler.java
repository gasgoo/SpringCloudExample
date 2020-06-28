package com.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date 2019/12/13 9:43
 * @name NettyEchoServerOutHandler
 */

@Slf4j
public class NettyEchoServerOutHandler extends ChannelOutboundHandlerAdapter {

    private ChannelHandlerContext ctx;
    private Object msg;
    private ChannelPromise promise;

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        this.ctx = ctx;
        this.msg = msg;
        this.promise = promise;
        ctx.write(msg, promise);
    }


}
