package com.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;

import java.util.Scanner;

/**
 * 请求丢弃服务端  netty都是基于 反应器模式的  一组反应器线程监听链接，然后处理器处理链接的逻辑。
 * @Date 2019/11/29 11:45
 * @name NettyDiscardServer
 */

@Slf4j
public class NettyDiscardServer {

    //netty服务器的启动类
    ServerBootstrap bootstrap = new ServerBootstrap();
    private  final int port;
    public NettyDiscardServer(int port){
        this.port=port;
    }

    public void startServer(){
        //创建反应器线程  bossGroup负责监听和接收新连接;   Reactor线程模型
        EventLoopGroup bossGroup =new NioEventLoopGroup();
        //worketGroup负责查询子通道的IO事件 执行handler处理器  默认是 CPU 核心数乘2
        EventLoopGroup workerGroup =new NioEventLoopGroup();

        try{
            //设置反应器线程组
            bootstrap.group(bossGroup,workerGroup);
            //设置nio类型的通道
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.localAddress(port);
            //缓冲通道 池化分配器
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            //是否开启TCP底层心跳机制 间隔2小时 7200秒
            bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
            //装配子通道流水线  通道初始化处理器 ChannelInitializer
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                //如果有新连接则new 一个通道
                @Override
                protected void initChannel(SocketChannel channel)throws Exception{
                    //向子通道流水线添加一个handler处理器
                    channel.pipeline().addLast(new NettyDiscardHandler());
                }
            });
            //帮定服务器  sync同步阻塞直到帮定成功
            ChannelFuture channelFuture = bootstrap.bind().sync();
            log.info("服务器启动成功,端口={}",channelFuture.channel().localAddress());
            //等待通道关闭的异步任务结束  自我阻塞 等待异步IO操作的结果
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();

        }catch (Exception e){
            log.info("启动异常",e);
        }finally {
            //关闭 EventLoopGroup  反应器线程组关闭
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port=8888;
        new NettyDiscardServer(port).startServer();
    }
}
