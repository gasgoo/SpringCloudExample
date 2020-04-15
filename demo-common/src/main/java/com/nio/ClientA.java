package com.nio;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @Date 2019/11/8 17:27
 * @name ClientA
 */


public class ClientA {

    public static void main(String[] args) throws IOException {
        startClient();
    }

    public static void startClient() throws IOException {
        SocketChannel socketChannel=SocketChannel.open(new InetSocketAddress("127.0.0.1",8000));
        System.out.println("客户端启动成功>>>>>>>clientA");


        Selector selector= Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        // 不断的自旋 等待连接完成
        while(!socketChannel.finishConnect()){
            System.out.println("连接中....");
        }
        System.out.println("连接成功!");
        //获取服务器的响应
        new Thread(new NioClientHandler(selector)).start();
        //发送数据
        socketChannel.write(Charset.forName("UTF-8").encode("Hello Server,I am clientA"));
        //发送到服务器
        //socketChannel.shutdownInput();
        //socketChannel.close();
    }
}
