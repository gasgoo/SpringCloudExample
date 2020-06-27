package com.bio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Desc
 * @Date 2020/6/25 18:07
 */
public class Client {

    public static void main(String[] args) throws IOException {

        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 10001);
        Socket socket = new Socket();
        socket.connect(address);

        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());


        //发送给请求给server
        outputStream.writeUTF("my name is client!");
        outputStream.flush();

        //接受server的返回
        System.out.println("server response:" + inputStream.readUTF());

    }
}
