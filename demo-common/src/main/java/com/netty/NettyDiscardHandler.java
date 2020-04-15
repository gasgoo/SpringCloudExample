package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * discard 服务端处理器  业务处理  继承入站ChannelInboundHandler接口的默认实现类
 * @Date 2019/11/29 14:07
 * @name NettyDiscardHandler
 */

@Slf4j
public class NettyDiscardHandler  extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext context,Object msg)throws Exception{

        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) {
                String req=new String(in.toString(Charset.forName("UTF-8")));
                log.info("receive>>>>" + req);
            }
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
