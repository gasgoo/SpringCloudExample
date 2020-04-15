package com.nio;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *  一个线程处理一个连接
 * @Date 2019/11/27 17:06
 * @name ConnectionPerThread
 */


public class ConnectionPerThread implements Runnable{

    private int port=8881;

    @Override
    public void run() {
        try{
            port++;
            ServerSocket serverSocket=new ServerSocket(port);
            while(!Thread.interrupted()){
                //为每个socket连接建立一个专属的处理器handler
                System.out.println("Now Thread>>>>>>"+Thread.currentThread().getName()+"port:  "+serverSocket.getLocalPort());
                Socket socket=serverSocket.accept();
                Handler handler=new Handler(socket);
                new Thread(handler).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    static class Handler implements Runnable{
        final Socket socket;
        Handler(Socket s){
            socket=s;
        }
        @Override
        public void run() {
            while(true){
                try{
                    byte[] input =new byte[1024];
                    socket.getInputStream().read(input);
                    byte[] output=null;
                    socket.getOutputStream().write(output);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ConnectionPerThread connectionPerThread=new ConnectionPerThread();
        Thread t=new Thread(connectionPerThread);
        t.start();



    }
}
