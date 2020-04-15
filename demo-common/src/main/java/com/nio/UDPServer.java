package com.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 *  使用 DatagramChannel接收客户端数据
 * @Date 2019/11/27 11:03
 * @name UDPServer
 */


public class UDPServer {

    public void receive() throws IOException {
        DatagramChannel datagramChannel=DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(new InetSocketAddress(8990));
        System.out.println("UDP服务器启动成功!");
        //开启一个通道选择器
        Selector selector=Selector.open();
        //通道注册到selector
        //如果要监控多种类型的事件
        int i = datagramChannel.validOps();
        System.out.println("通道支持的事件:"+i);
        int key= SelectionKey.OP_READ |SelectionKey.OP_WRITE;
        datagramChannel.register(selector, key);
        //通过选择器 查询就绪的iO 事件
        while(selector.select()>0){
            Iterator<SelectionKey> iterator=selector.selectedKeys().iterator();
            ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
            //迭代IO事件
            while(iterator.hasNext()){
                SelectionKey selectionKey=iterator.next();
                //判断当前查询到的IO事件是什么事件
                if(selectionKey.isReadable()){
                    //读取DatagramChannel数据通道的数据  接收客户端的数据
                    SocketAddress client=datagramChannel.receive(byteBuffer);
                    //切换为读取模式
                    byteBuffer.flip();
                    String read=new String(byteBuffer.array(),0,byteBuffer.limit());
                    System.out.println(">>"+read);
                    byteBuffer.clear();
                }
            }
            //处理完成这移除当前连接事件
            iterator.remove();
        }
        System.out.println("服务器处理完成!");
        selector.close();
        datagramChannel.close();
    }

    public static void main(String[] args) throws IOException {
        new UDPServer().receive();
    }
}
