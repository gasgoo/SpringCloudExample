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

    private static final String host="127.0.0.1";
    private static final int port=8000;
    private static NioClientHandlerA nioClientHandlerA;
    public static void main(String[] args) throws IOException {
        startClient();
    }

    public static void startClient() throws IOException {

        if(nioClientHandlerA!=null){
            nioClientHandlerA.stop();
        }
        nioClientHandlerA=new NioClientHandlerA(host,port);
        new Thread(nioClientHandlerA).start();
    }

    //发送数据
    public static void sendMsg(String msg) throws IOException {
        nioClientHandlerA.sendMsg(msg);
    }
}
