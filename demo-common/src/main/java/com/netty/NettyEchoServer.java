package com.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @netty 请求回显服务端
 * @Date 2019/12/12 19:12
 * @name NettyEchoServer
 */

@Slf4j
public class NettyEchoServer {

    /**
     *  ServerBootStrap的装配和使用
     *  处理器 channelRead()的入站处理方法
     *  ByteBuf缓冲区的读取 写入 引用计数器的查看
     *
     **/
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    public void runServer() throws InterruptedException {
        EventLoopGroup  boosGroup =new NioEventLoopGroup(1);
        EventLoopGroup workerGroup =new NioEventLoopGroup();
        serverBootstrap.group(boosGroup,workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.localAddress(8888);
        serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        //是否开启TCP底层心跳机制 间隔2小时 7200秒
        serverBootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        //装配子通道流水线  通道初始化处理器 ChannelInitializer
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            //如果有新连接则new 一个通道
            @Override
            protected void initChannel(SocketChannel channel)throws Exception{
                //向子通道流水线添加一个handler处理器
                channel.pipeline().addLast(new NettyEchoServerHandler());
                channel.pipeline().addLast(new NettyEchoServerOutHandler());
            }
        });
        //帮定服务器  sync同步阻塞直到帮定成功
        ChannelFuture channelFuture = serverBootstrap.bind().sync();
        log.info("服务器启动成功,端口={}",channelFuture.channel().localAddress());
        //等待通道关闭的异步任务结束  自我阻塞 等待异步IO操作的结果
        ChannelFuture closeFuture = channelFuture.channel().closeFuture();
        closeFuture.sync();
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyEchoServer().runServer();
    }
}
