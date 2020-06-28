package com.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;

import java.util.Scanner;

/**
 * @Date 2019/12/13 8:54
 * @name NettyEchoClient
 */

@Slf4j
public class NettyEchoClient {

    private int serverPort;
    private String serverIp;

    private Bootstrap bootstrap =new Bootstrap();

    public NettyEchoClient(String ip,int port){
        this.serverIp=ip;
        this.serverPort=port;
    }

    public void runClient()  {
        EventLoopGroup  workerLoopGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(workerLoopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.remoteAddress(serverIp, serverPort);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(NettyEchoClientHandler.INSTANCE);
                }
            });
            //发起连接
            ChannelFuture f = bootstrap.connect();

            f.addListener((ChannelFuture future) -> {
                if (future.isSuccess()) {
                    log.info("NettyEchoClient 启动成功!");
                } else {
                    log.info("NettyEchoClient 连接失败!");
                }
            });
            //阻塞直到连接完成
            f.sync();

            Channel channel = f.channel();
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入内容>>");
            while (scanner.hasNext()) {
                String text = scanner.next();
                byte[] bytes = (DateUtil.formatAsDatetime(DateUtil.now())+":"+ text).getBytes("UTF-8");
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(bytes);
                channel.writeAndFlush(buffer);
            }
        }catch (Exception e){
            log.error("exception :",e);

        }finally {
            workerLoopGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new NettyEchoClient("127.0.0.1",8888).runClient();
    }

}
