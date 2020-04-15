package com.nio;

import com.google.common.base.Strings;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * NIO客户端实现
 * @Date 2019/11/8 11:06
 * @name NioClient
 */


public class NioClient {

    private String clientName;
    public NioClient(String name){
        this.clientName=name;
    }

    //启动客户端
    public void start() throws IOException {
        //1 和服务器建立连接
        SocketChannel socketChannel=SocketChannel.open(new InetSocketAddress("127.0.0.1",8000));
        System.out.println("客户端启动成功>>>>>>>"+clientName);


        Selector selector= Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 接收服务器响应  新开线程 用来接收响应数据
        new Thread(new NioClientHandler(selector)).start();
        socketChannel.write(Charset.forName("UTF-8").encode("Hello Server,I am NIOClient"));


        //2 向服务器发送数据
        Scanner scanner=new Scanner(System.in);
        while(scanner.hasNextLine()){
            String request=scanner.nextLine();
            if(!Strings.isNullOrEmpty(request)){
                socketChannel.write(Charset.forName("UTF-8").encode(request));
            }
        }



    }

    public static void main(String[] args) throws IOException {
        NioClient nioClient=new NioClient("群主Client");
        nioClient.start();
    }
}
