package com.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.extern.slf4j.Slf4j;

/**
 * Http服务器
 * @Date 2020/6/29 19:53
 * @name HttpServer
 */

@Slf4j
public class HttpServer {

    //设置服务端端口
    public static final int port = 6789;
    // 通过nio方式来接收连接和处理连接
    private static EventLoopGroup group = new NioEventLoopGroup();
    private static ServerBootstrap b = new ServerBootstrap();
    private static final boolean SSL = true ;

    public void runServer() throws Exception {
        final SslContext sslContext;
        if(SSL){
            SelfSignedCertificate ssc=new SelfSignedCertificate();
            sslContext= SslContextBuilder.forServer(ssc.certificate(),ssc.privateKey()).build();
        }else{
            sslContext=null;
        }
        try{
            b.group(group);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ServerHandlerInit(sslContext));
            ChannelFuture sync = b.bind(port).sync();
            log.info("服务启动成功,端口号:"+port);
            //关闭服务器监听
            sync.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new HttpServer().runServer();
    }

}
