package com.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 阻塞IO
 * @Date 2020/3/30 15:13
 * @name BIOMySqlServer
 */


public class BIOMySqlServer {

    private static byte[] bs=new byte[1024];
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket= new ServerSocket(9098);
        while(true){
            System.out.println("BIOMySqlServer等待连接...");
            //阻塞 放弃了cpu 接收到一个连接后
            Socket clientSocket =serverSocket.accept();
            System.out.println("connection success  等待数据");
            //阻塞 放弃cpu
            int read = clientSocket.getInputStream().read(bs);
            if(read >0){
                System.out.println(new String(bs));
            }

        }
    }
}
