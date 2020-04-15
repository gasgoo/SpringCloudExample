package com.nio;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * 阻塞BIO
 * @Date 2020/3/30 15:15
 * @name BIOMySqlClient
 */


public class BIOMySqlClient {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("127.0.0.1",9098);

        Scanner scanner =new Scanner(System.in);
        String next =scanner.next();
        next="BIOMySqlClient connection: "+next;
        socket.getOutputStream().write(next.getBytes("UTF-8"));
        //socket.close();
    }
}
