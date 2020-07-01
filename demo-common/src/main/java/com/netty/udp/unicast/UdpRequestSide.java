package com.netty.udp.unicast;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * udp协议请求端  单广播
 * @Date 2020/7/1 13:57
 * @name UdpRequestSide
 */

@Slf4j
public class UdpRequestSide {

    public final  static  String QUESTION="告诉我一句古诗";
    public final  static  String HOST="127.0.0.1";


    EventLoopGroup group=new NioEventLoopGroup();
    public void run(int port){
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioDatagramChannel.class);
            bootstrap.handler(new RequestHandler());

            //udp不需要建立连接
            Channel channel = bootstrap.bind(0).sync().channel();
            channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(QUESTION, CharsetUtil.UTF_8),new InetSocketAddress(HOST,port)));
            //超过15秒则超时
            if(!channel.closeFuture().await(15000)){
                log.info("请求超时!");
            }

        }catch (Exception e){
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port=8000;
        new UdpRequestSide().run(port);
    }
}
