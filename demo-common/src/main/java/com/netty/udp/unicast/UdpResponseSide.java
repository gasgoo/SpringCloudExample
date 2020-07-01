package com.netty.udp.unicast;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * udp协议响应端
 * @Date 2020/7/1 14:12
 * @name UdpResponseSide
 */

@Slf4j
public class UdpResponseSide {

    public final static String ANSWER = "古诗来了：";
    public void runServer(int port){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioDatagramChannel.class);
            bootstrap.handler(new ResponseHandler());

            ChannelFuture channel = bootstrap.bind(port).sync();
            log.info("应答服务已启动.....");
            channel.channel().closeFuture().sync();
        }catch (Exception e){
            log.error("Exception:{}",e);
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String [] args) throws Exception{
        int port = 8000;
        new UdpResponseSide().runServer(port);
    }
}
