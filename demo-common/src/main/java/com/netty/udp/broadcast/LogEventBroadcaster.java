package com.netty.udp.broadcast;

import com.netty.udp.LogConst;
import com.netty.udp.LogMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * 日志广播端
 * @Date 2020/7/1 15:18
 * @name LogEventBroadcaster
 */

@Slf4j
public class LogEventBroadcaster {

    private final EventLoopGroup group;
    private final Bootstrap bootstrap;

    public LogEventBroadcaster(InetSocketAddress socketAddress){
        group=new NioEventLoopGroup();
        bootstrap=new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioDatagramChannel.class);
        //设置 SO_BROADCAST 套接字选项
        bootstrap.option(ChannelOption.SO_BROADCAST,true);
        bootstrap.handler(new LogEventEncoder(socketAddress));

    }

    public void run() throws InterruptedException {
        Channel channel = bootstrap.bind(0).sync().channel();
        long count=0;
        for(;;){
            channel.writeAndFlush(new LogMsg(null, ++count, LogConst.getLogInfo()));
            try{Thread.sleep(2000);}
            catch (Exception e){
                Thread.interrupted();
                break;
            }
        }
    }

    public void stop(){
        group.shutdownGracefully();
    }
    public static void main(String[] args) throws Exception {

        //创建并启动一个新的  的实例
        LogEventBroadcaster broadcaster = new LogEventBroadcaster(
                //表明本应用发送的报文并没有一个确定的目的地，也就是进行广播
                new InetSocketAddress("255.255.255.255",
                        LogConst.MONITOR_SIDE_PORT));
        try {
            broadcaster.run();
            log.info("日志广播端启动完成......");
        }
        finally {
            broadcaster.stop();
        }
    }
}
