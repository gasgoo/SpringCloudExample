package com.nio;

import org.assertj.core.util.DateUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

/**
 * 通过 DatagramChannel 通道发送数据给服务器
 * @Date 2019/11/27 11:15
 * @name UDPClient
 */


public class UDPClient {

    public void send() throws IOException {
        DatagramChannel datagramChannel=DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        Scanner scanner=new Scanner(System.in);
        System.out.println("UDP客户端启动成功!");
        System.out.println("请输入发送内容: ");
        while(scanner.hasNext()){
            String next=scanner.next();
            //缓存写数据
            buffer.put(next.getBytes("UTF-8"));
            buffer.flip();
            datagramChannel.send(buffer,new InetSocketAddress("127.0.0.1",8990));
            //切换为写模式
            buffer.clear();
        }
        datagramChannel.close();

    }

    public static void main(String[] args) throws IOException {
        new UDPClient().send();
    }

}
